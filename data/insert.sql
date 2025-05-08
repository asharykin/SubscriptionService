INSERT INTO subscriptions (service_name)
VALUES
    ('YouTube Premium'),
    ('VK Музыка'),
    ('Яндекс.Плюс'),
    ('Netflix'),
    ('Spotify'),
    ('Apple Music');

INSERT INTO users (username, email, password)
VALUES
    ('john_doe', 'john.doe@example.com', 'password123'),
    ('jane_smith', 'jane.smith@example.com', 'securepass'),
    ('peter_jones', 'peter.jones@example.com', 'mysecret'),
    ('alice_brown', 'alice.brown@example.com', 'alicepass');

INSERT INTO users_subscriptions (user_id, subscription_id, start_date, end_date)
VALUES
    -- YouTube Premium
    (1, 1, '2024-01-01', '2025-01-01'),
    (2, 1, '2024-02-15', '2025-02-15'),
    (3, 1, '2024-03-01', '2025-03-01'),
    (4, 1, '2024-04-10', '2025-04-10'),
    -- VK Музыка
    (2, 2, '2024-03-01', '2025-03-01'),
    (3, 2, '2024-04-10', '2025-04-10'),
    (1, 2, '2024-05-01', '2025-05-01'),
    -- Яндекс.Плюс
    (1, 3, '2024-02-15', '2025-02-15'),
    (4, 3, '2024-03-01', '2025-03-01'),
    (2, 3, '2024-04-10', '2025-04-10'),
    -- Netflix
    (3, 4, '2024-04-10', '2025-04-10'),
    (4, 4, '2024-05-01', '2025-05-01'),
    -- Spotify
    (1, 5, '2024-04-10', '2025-04-10'),
    (2, 5, '2024-05-01', '2025-05-01'),
    (3, 5, '2024-06-01', '2025-06-01'),
    -- Apple Music
    (4, 6, '2024-03-01', '2025-03-01'),
    (1, 6, '2024-04-10', '2025-04-10');