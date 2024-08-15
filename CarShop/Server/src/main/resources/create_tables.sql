CREATE TABLE users (id INTEGER not NULL, name VARCHAR(255), surname VARCHAR(255), username VARCHAR(255), password VARCHAR(255),
                            role VARCHAR(255), PRIMARY KEY (id));

CREATE TABLE cars (id INTEGER not NULL, brand VARCHAR(255), model VARCHAR(255), yearOfIssue DATE NOT NULL,
                   price NUMERIC NOT NULL, state VARCHAR(255),inStock BOOLEAN NOT NULL, PRIMARY KEY (id));

CREATE TABLE orders (id INTEGER not NULL, status VARCHAR(255), userId INTEGER REFERENCES users (id),
                     carId INTEGER REFERENCES cars (id), PRIMARY KEY ( id ));

CREATE TABLE logging_users_orders (id INTEGER not NULL, userId INTEGER REFERENCES users (id),
                                   action VARCHAR(255), date DATE NOT NULL, PRIMARY KEY (id));