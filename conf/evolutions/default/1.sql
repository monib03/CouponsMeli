# --- !Ups
CREATE TABLE items
(
  id                   VARCHAR(100),
  price                NUMERIC(1000, 2) NOT NULL,
  CONSTRAINT items_pk  PRIMARY KEY (id)
);
COMMENT ON TABLE items IS 'Tabla con la información de los items';
COMMENT ON COLUMN items.id IS 'Id del item';
COMMENT ON COLUMN items.price IS 'Precio del item';

CREATE TABLE users
(
  id                   VARCHAR(100),
  CONSTRAINT users_pk  PRIMARY KEY (id)
);
COMMENT ON TABLE users IS 'Tabla con la información de los usuarios';
COMMENT ON COLUMN users.id IS 'Id del usuario';

CREATE TABLE bookmarks
(
  item_id               VARCHAR(100),
  user_id               VARCHAR(100),
  bookmarked_date       TIMESTAMP NOT NULL,
  CONSTRAINT items_fk FOREIGN KEY (item_id) REFERENCES items (id),
  CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES users (id)
);
COMMENT ON TABLE bookmarks IS 'Tabla con la información de los items favoritos de los usuarios';
COMMENT ON COLUMN bookmarks.user_id IS 'Id del usuario';
COMMENT ON COLUMN bookmarks.item_id IS 'Id del item';

# --- !Downs
DROP TABLE bookmarks;
DROP TABLE items;
DROP TABLE users;
