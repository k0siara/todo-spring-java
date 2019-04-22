insert into activation_tokens (value, expires_at, created_at, updated_at)
    values ('4611db30608811e98647d663bd873d93', current_timestamp, current_timestamp, current_timestamp),
           ('02ea8794608811e98647d663bd873d93', current_timestamp, current_timestamp, current_timestamp);

insert into users (first_name, last_name, username, email, password, is_enabled, activation_token_id, created_at, updated_at)
    values ('Patryk', 'Kosieradzki', 'k0siara', 'patryk.kosieradzki@gmail.com',
            '$2a$10$y04XYBMtkSYa8qLzE.PN7OiDvtF6PBZrGyTPJRgTDhEfFngYoV.MK', true, 1, current_timestamp, current_timestamp),

           ('Adrian', 'Twarowski', 'atwarowski', 'adrian.twarowski@gmail.com',
             '$2a$10$iQE13/7QBfkSjnKbOFJucu5PKMwXxjTBEOtkY2nHhMCHgW1vD0gFS', false, 2, current_timestamp, current_timestamp);


insert into roles (name, created_at, updated_at)
    values ('ROLE_ADMIN', current_timestamp, current_timestamp),
           ('ROLE_USER', current_timestamp, current_timestamp);

insert into user_roles (user_id, role_id) values (1, 1), (2, 2)