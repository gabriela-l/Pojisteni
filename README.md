# CRUD aplikace Pojištění

Aplikace Pojištění obsahuje kompletní správu pojištěných a jejich pojištění.
Využívá **Spring Boot framework, Maven, Thymeleaf, Hibernate, Bootstrap** a **MySQL**. Aplikace byla vytvořena v prostředí IntelliJ.

## O aplikaci
Pro přístup do databáze se uživatel zaregistruje a přihlásí, čímž se zároveň vytvoří jeho záznam v databázi.
Aplikace rozlišuje uživatelské role, přičemž je možné si vybrat z **pojištěnce** (v aplikaci a datbázi jako User),
který může záznamy pouze zobrazit, **editora** (může upravovat uživatele i pojištění) a **admina**,
který může navíc záznamy odstranit a vytvářet nové pojištěnce a pojištění.
Záznamy jsou uloženy v MySQL databázi (správa v MySQL Workbench). Při smazání pojištěnce jsou smazána všechna jeho pojištění.

### Spuštění aplikace
V MySQL vytvořte novou databázi s názvem **Pojisteni**. Otevřete složku Pojisteni v IDE (IntelliJ) a v application.yml zadejte svoje přihlašovací údaje k databázi.
Spusťte PojisteniApplication (v databázi se vytvoří tabulky users, roles, insurance).
Nyní je potřeba **změnit nastavení** práce s databází z create na update:
*hibernate.ddl-auto: update*

Aplikace běží na adrese http://localhost:8080

### Připravené SQL příkazy pro vyzkoušení funkcionality:

V databázi vytvořte **uživatelské role**:

INSERT INTO Pojisteni.roles VALUES (1, "User");

INSERT INTO Pojisteni.roles VALUES (2, "Editor");

INSERT INTO Pojisteni.roles VALUES (3, "Admin");


Hesla zadaná uživatelem na frontendu aplikace se vkládají do databáze již šifrovaná,
připravila jsem proto zašifrovaná hesla testovacích uživatelů pomocí třídy PasswordEncoder.

#### Testovací uživatelé:

INSERT INTO Pojisteni.users VALUES (1, 'jan.vesely@gmail.com', 1, '$2a$10$cFMsLqbZ9aKq9Uw36b8beeCycrh4VpYqCfNuVZnV6L7mEeVjrS70u', 'Jan', 'Veselý', 3);

INSERT INTO Pojisteni.users VALUES (2, 'klara.novak@gmail.com', 1, '$2a$10$ImyTHeijETmNFU9MOhjWoOxvHPQQrAcPtaNFczDUqZqt.TK7iyJUe', 'Klára', 'Nováková', 2);

INSERT INTO Pojisteni.users VALUES (3, 'anna.kovarova@gmail.com', 1, '$2a$10$gc.6HVbLtJ70ZVvXpi2ofe7lhE.KjCloriNFABxb72uuoGHt4nA2q', 'Anna', 'Kovářová', 3);

INSERT INTO Pojisteni.users VALUES (4, 'pavel.sedlacek@gmail.com', 1, '$2a$10$A0kP0M1sN4xYTvRbGVbiJOz6WGD99O.IPhmDS6eb2SxqL456yl0wG', 'Pavel', 'Sedláček', 1);

#### Nešifrovaná hesla pro přihlášení:

(email - heslo - role_id)

`jan.vesely@gmail.com` - janvesely4 - Admin

`klara.novak@gmail.com` - novak13 - Editor

`anna.kovarova@gmail.com` - kovarova9 - Admin

`pavel.sedlacek@gmail.com` - pavSedl3 - User (Pojištěnec)



#### Pojištění:

INSERT INTO Pojisteni.insurance VALUES (1, '300781228', 'standart', 'Cestovní pojištění Uniqa', '2021-01-12', '2021-31-12', 1)

INSERT INTO Pojisteni.insurance VALUES (2, '230882117', 'ideal', 'Pojištění majetku Maxima', '2021-01-07', '2022-01-07', 1)

INSERT INTO Pojisteni.insurance VALUES (3, '20338129', 'standart', 'Pojištění domácnosti Maxima', '2021-01-12', '2022-01-12', 2)

INSERT INTO Pojisteni.insurance VALUES (4, '300781228', 'extra', 'Úrazové pojištění Generali', '2021-01-12', '2021-31-12', 3)

*Pojištěnec tedy může mít více pojištění, např. pojištěnec s ID 1 (jsou vypsány v detailu pojištěnce).*

