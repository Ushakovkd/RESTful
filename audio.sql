
CREATE TYPE role_t as ENUM('ADMIN', 'CUSTOMER');

CREATE TABLE users (
user_id SERIAL PRIMARY KEY,
role role_t NOT NULL,
login VARCHAR (20) NOT NULL,
password VARCHAR (32) NOT NULL,
discount INT DEFAULT '0' CHECK (discount BETWEEN 0 and 100),
block BOOLEAN DEFAULT '0');

CREATE TABLE artist(
artist_id SERIAL PRIMARY KEY,
artist_name VARCHAR(20) NOT NULL);

CREATE TYPE genre_t as ENUM('CLASSICAL', 'ELECTRONIC', 'JAZZ', 'POP', 'RAP', 'ROCK', 'REGGAE');

CREATE TABLE song (
song_id SERIAL PRIMARY KEY,
artist_id INT NOT NULL,
title VARCHAR(30) NOT NULL,
genre genre_t NOT NULL,
price FLOAT NOT NULL,
FOREIGN KEY (artist_id) REFERENCES artist(artist_id) ON DELETE CASCADE);

CREATE TABLE user_songs (
user_id INT NOT NULL,
song_id INT NOT NULL,
PRIMARY KEY (user_id, song_id),
FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
FOREIGN KEY (song_id) REFERENCES song (song_id) ON DELETE CASCADE);

CREATE TABLE comment (
comment_id SERIAL PRIMARY KEY,
song_id INT NOT NULL,
user_id INT NOT NULL,
text VARCHAR(200) NOT NULL,
FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
FOREIGN KEY (song_id) REFERENCES song (song_id) ON DELETE CASCADE);

CREATE TYPE collection_t as ENUM('album','collection');

CREATE TABLE collection (
collection_id SERIAL PRIMARY KEY,
artist_id INT,
title VARCHAR(20) NOT NULL,
genre genre_t NOT NULL,
type collection_t NOT NULL,
FOREIGN KEY (artist_id) REFERENCES artist(artist_id) ON DELETE CASCADE);

CREATE TABLE collection_songs(
collection_id INT NOT NULL,
song_id INT NOT NULL,
PRIMARY KEY (collection_id, song_id),
FOREIGN KEY (collection_id) REFERENCES collection(collection_id) ON DELETE CASCADE,
FOREIGN KEY (song_id) REFERENCES song(song_id) ON DELETE CASCADE);

INSERT INTO users (role, login, password) VALUES
('ADMIN','admin', MD5('admin')),
('CUSTOMER', 'man', MD5('1111')),
('CUSTOMER', 'dog', MD5('1111')),
('CUSTOMER', 'cat', MD5('1111')),
('CUSTOMER', 'fish', MD5('1111'));

INSERT INTO artist (artist_name) VALUES
('HIM'),
('Dr.Alban'),
('Lois Armstrong'),
('Off spring'),
('Metallica'),
('Rammstein'),
('Sade'),
('Eminem'),
('Scorpions'),
('Moby'),
('Kottonmouth Kings'),
('Frank Sinatra'),
('Nat King Cole'),
('Ray Charles'),
('Linkin Park');

INSERT INTO song (artist_id, title, genre, price)  VALUES
('1', 'Man', 'ROCK', '5.55'),
('2', 'No coke', 'POP', '7.45'),
('3', 'Beautiful dream', 'JAZZ', '8.08'),
('4', 'Mota', 'ROCK', '5.45'),
('5', 'Nothing else matters', 'ROCK', '7.01'),
('6', 'Du hast', 'ROCK', '6.12'),
('7', 'Love is found', 'JAZZ', '5.35'),
('8', '8 Mile', 'RAP', '4.45'),
('9', 'No pain no gain', 'ROCK', '8.44'),
('10', 'Porcelain', 'POP', '9.32'),
('11', 'Love lost', 'RAP', '4.45'),
('12', 'Witchcraft', 'JAZZ', '6.78'),
('13', 'Smile', 'JAZZ', '5.69'),
('14', 'Georgia on my mind', 'JAZZ', '7.05'),
('15', 'Foreword', 'ROCK', '5.01'),
('15', 'Don''t stay', 'ROCK', '5.98'),
('15', 'Somewhere I Belong', 'ROCK', '5.74'),
('15', 'Lying from You', 'ROCK', '5.45'),
('15', 'Hit the Floor', 'ROCK', '5.65'),
('15', 'Numb', 'ROCK', '5.78'),
('8', 'The Kiss', 'RAP', '4.96'),
('8', 'White America', 'RAP', '3.41'),
('8', 'Business', 'RAP', '4.35'),
('8', 'Cleanin'' Out My Closet', 'RAP', '5.12'),
('8', 'Soldier', 'RAP', '5.45');

INSERT INTO comment (song_id, user_id, text) VALUES
('1','2', 'Very nice!'),
('1','3', 'Woof!'),
('1','4', 'Meow!'),
('2','3', 'I am loving it!');

INSERT INTO collection (type, artist_id, title, genre) VALUES
('album','15', 'Meteora', 'ROCK'),
('album','8', 'The show', 'RAP');
INSERT INTO collection (type, title, genre) VALUES
('collection','Popular', 'POP'),
('collection','Relax', 'POP');

INSERT INTO collection_songs (collection_id, song_id) VALUES
('1', '15'),
('1', '16'),
('1', '17'),
('1', '18'),
('1', '19'),
('1', '20'),
('2', '21'),
('2', '22'),
('2', '23'),
('2', '24'),
('2', '25'),
('3','2'),
('3','10'),
('4','3'),
('4','7'),
('4','13'),
('4','14');

INSERT INTO user_songs (user_id, song_id) VALUES
('2', '3'),
('2', '4'),
('3', '1');

UPDATE collection SET artist_id='15', title='METERORA', genre='ROCK' WHERE collection_id='1';

SELECT song_id, artist_name, title, price, genre FROM song NATURAL JOIN artist WHERE song_id IN (SELECT song_id FROM user_songs WHERE user_id = '2');