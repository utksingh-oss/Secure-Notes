
### While Working found the Issue
Sure — here is a **clean, copy-ready version** of your stacktrace followed by a clear explanation you can save for later.

---

# ✅ **Cleaned & Complete Stacktrace (copy-paste ready)**

```
org.postgresql.util.PSQLException: Large Objects may not be used in auto-commit mode.
	at org.postgresql.largeobject.LargeObjectManager.open(LargeObjectManager.java:244) ~[postgresql-42.7.8.jar:42.7.8]
	at org.postgresql.largeobject.LargeObjectManager.open(LargeObjectManager.java:230) ~[postgresql-42.7.8.jar:42.7.8]
	at org.postgresql.jdbc.AbstractBlobClob.getLo(AbstractBlobClob.java:288) ~[postgresql-42.7.8.jar:42.7.8]
	at org.postgresql.jdbc.AbstractBlobClob.getBinaryStream(AbstractBlobClob.java:127) ~[postgresql-42.7.8.jar:42.7.8]
	at org.postgresql.jdbc.PgClob.getCharacterStream(PgClob.java:76) ~[postgresql-42.7.8.jar:42.7.8]
```

---

# 📘 **Explanation of the Error (save this for your notes)**

### **What the error means**

The root cause in your stacktrace is:

```
org.postgresql.util.PSQLException: Large Objects may not be used in auto-commit mode.
```

This is a PostgreSQL-specific rule:

* PostgreSQL stores large `@Lob` fields (CLOB/BLOB) using its **Large Object API**
* Large Objects **cannot be accessed when the database connection is in auto-commit mode**
* But your datasource is currently using `autoCommit = true` (Hikari default)

So when Hibernate tries to read a `@Lob` field (CLOB), PostgreSQL throws this exception.

---

# 🔍 Why it happened

Hibernate loads LOBs **as streams**, not as a simple field.

To read a stream, PostgreSQL requires:

* An **active transaction**
* Auto-commit **disabled**

If:

* The method is not annotated with `@Transactional`, **OR**
* HikariCP’s `auto-commit` is true,

…the LOB load fails.

---

# 🎯 The fix (summary)

### **1️⃣ Disable auto-commit**

In `application.properties`:

```properties
spring.datasource.hikari.auto-commit=false
```

### **2️⃣ Wrap your JPA read method with @Transactional**

```java
@Transactional
public MyEntity getEntity(Long id) {
    return repository.findById(id).orElseThrow();
}
```

### **3️⃣ OPTIONAL (recommended)**

Avoid PostgreSQL Large Object API by using:

```java
@Column(columnDefinition = "TEXT")
@Lob
private String content;
```

or

```java
@Column(columnDefinition = "BYTEA")
@Lob
private byte[] fileData;
```

This forces Hibernate to use PostgreSQL’s regular TEXT/BYTEA instead of true LOB/OID.

---
We are seeing **a number instead of the actual content** in PostgreSQL because your LOB field is being stored as a **PostgreSQL Large Object (OID type)** — not as TEXT/BYTEA.

This is **expected behavior**, not a bug.

---

# ✅ Why you see only a number in PostgreSQL

PostgreSQL supports large objects in two ways:

### 1. **BYTEA / TEXT**

Stores actual data directly in the table.
You see the full string or binary content in SQL.

### 2. **Large Object (OID)** 👈 **THIS is what you're seeing**

Postgres stores:

* In your table → only the **OID number** (an integer like `12345`)
* Actual data → inside its system catalog `pg_largeobject`

So your table looks like this:

| id | lob_column |
| -- | ---------- |
| 1  | 24589      |

That `24589` is **not your content**.
It is the **pointer** to the actual large object.

---

# 🧠 Why JPA/Hibernate is generating an OID (number)?

Because you used:

```java
@Lob
String content;
```

Hibernate maps CLOB/TEXT LOBs as:

* **OID** in PostgreSQL by default

Thus you see the **OID number**, not the real content.

But Hibernate automatically uses the Large Object API (`pg_largeobject`) to read the actual content — that’s why your API returns the correct value even though PostgreSQL shows only a number.

---

# 🧪 How to verify the real content in PostgreSQL

Run:

```sql
SELECT * FROM pg_largeobject WHERE loid = <your_oid_number>;
```

You’ll see the actual chunks of your LOB.

But it's not user-friendly — PostgreSQL stores LOBs in pieces.

---

# 👍 Should you keep using OID-based Large Objects?

Usually **NO**.
OID LOBs have drawbacks:

* Require a manual transaction (your current error)
* Can't be queried easily
* Harder to maintain and debug
* Garbage collection needed for orphan LOBs

---

# ✅ Recommended approach (Best Practice)

Instead of letting Hibernate choose OID, **explicitly store the content as TEXT or BYTEA**.

### For text content:

```java
@Lob
@Column(columnDefinition = "TEXT")
private String content;
```

Now PostgreSQL stores the **actual string**, not an OID.

### For file/binary content:

```java
@Lob
@Column(columnDefinition = "BYTEA")
private byte[] data;
```

Now PostgreSQL stores real binary, not an OID.

---

# 📌 Summary — Why you see a number

* PostgreSQL stores LOBs as **OID references**
* Your `@Lob` field defaults to CLOB → PostgreSQL Large Object
* Database column contains only the **OID pointer**
* Actual content is in `pg_largeobject`
* Hibernate loads it correctly through its LOB API, but psql/clients show only the OID number

---

Thanks — this makes everything clear.
Your issue comes **exactly** from this:

```java
@Lob
private String content;
```

When you use `@Lob` with `String`, Hibernate tells PostgreSQL:

> “Store this as a CLOB (Character Large Object).”

And **PostgreSQL does NOT store CLOBs in the table**.
It stores them in `pg_largeobject` and places only an **OID number** (integer pointer) in your `note.content` column.

That is why you see:

* API response → correct content
* PostgreSQL table → just a number
* Hibernate → needs a transaction to load the LOB stream
* PostgreSQL → throws

  ```
  Large Objects may not be used in auto-commit mode
  ```

---

# ✅ **How to fix your Entity properly**

Since your data is text (Markdown or notes), you **should not** use PostgreSQL Large Objects at all.

Replace this:

```java
@Lob
private String content;
```

With this **recommended mapping**:

```java
@Lob
@Column(columnDefinition = "TEXT")
private String content;
```

Or even simply:

```java
@Column(columnDefinition = "TEXT")
private String content;
```

### Why this works:

* Hibernate stores the full string directly in the table (`TEXT` column)
* PostgreSQL does NOT use OID or `pg_largeobject`
* No streaming → no "large objects may not be used in auto-commit mode" errors
* You can read the content normally from DB
* No need for `@Transactional` just to read notes

---

# ❗ What happens if you do NOT add the `columnDefinition = "TEXT"`?

Hibernate defaults to:

* PostgreSQL **OID-based large object**
* Which:

    * stores only a pointer (number)
    * requires LOB streaming
    * forces you into transaction issues
    * causes your current error

---

# 🧪 After updating the entity

Run a migration:

```
ALTER TABLE note
    ALTER COLUMN content TYPE TEXT;
```

Or let Hibernate recreate the schema if you're not in production.

---

# 🧠 Summary

### Your issue happens because:

| Behavior             | Reason                                                |
| -------------------- | ----------------------------------------------------- |
| DB shows **numbers** | OID → Large Object pointers                           |
| You get API content  | Hibernate streams LOB correctly                       |
| You get errors       | PostgreSQL LOBs require transactions + no auto-commit |
| Hard to debug        | LOB data stored in `pg_largeobject`                   |

### Best fix:

✔ **Use TEXT instead of PostgreSQL Large Objects**
✔ Removes all LOB-related issues
✔ Cleaner, simpler, faster

---
