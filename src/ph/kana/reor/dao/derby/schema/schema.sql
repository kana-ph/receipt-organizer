CREATE TABLE attachment (
	id BIGINT
		NOT NULL
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	path VARCHAR(32672)
		NOT NULL,
	document_id BIGINT
		NOT NULL
);

CREATE TABLE category (
	id BIGINT
		NOT NULL
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	value VARCHAR(32672) NOT NULL
);

CREATE TABLE document (
	id BIGINT
		NOT NULL
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	title VARCHAR(32672)
		NOT NULL,
	document_date DATE,
	description VARCHAR(32672)
);

CREATE TABLE receipt (
	id BIGINT
		NOT NULL,
	amount DECIMAL(19, 2),
	warranty_id BIGINT,
	category_id BIGINT
);

CREATE TABLE warranty (
	id BIGINT
		NOT NULL
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	expiration DATE,
	document_id BIGINT
		NOT NULL
);

ALTER TABLE attachment
	ADD FOREIGN KEY(document_id).
	REFERENCES document(id);

ALTER TABLE receipt
	ADD FOREIGN KEY(id)
	REFERENCES document(id);

ALTER TABLE receipt
	ADD FOREIGN KEY(warranty_id)
	REFERENCES warranty(id);

ALTER TABLE receipt
	ADD FOREIGN KEY(category_id)
	REFERENCES category(id);

ALTER TABLE warranty
	ADD FOREIGN KEY(document_id)
	REFERENCES warranty(id);
