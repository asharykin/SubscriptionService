DROP TABLE IF EXISTS users_subscriptions;

DROP TABLE IF EXISTS subscriptions;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    service_name TEXT NOT NULL
);

CREATE TABLE users_subscriptions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    subscription_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(id),
    UNIQUE (user_id, subscription_id)
);
