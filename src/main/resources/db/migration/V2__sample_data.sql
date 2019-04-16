insert into users(first_name, last_name, username, email, password, activation_token, is_enabled) values

  ('Patryk', 'Kosieradzki', 'k0siara', 'patryk.kosieradzki@gmail.com',
   '$2a$15$BM9JWn3g.bPIARI.ru7Pe.MhwFxuWP9RyeLTutJADD9JMwyjewNEm', '4611db30608811e98647d663bd873d93', true),

  ('Adrian', 'Twarowski', 'atwarowski', 'adrian.twarowski@gmail.com',
   '$2a$15$b5CbWsoafy4pSlRrJhLQPOG4hF.TyvgSiT0UeVQHu95Dc8Ae.4tVG', '02ea8794608811e98647d663bd873d93', false);


insert into roles (name) values ('ROLE_ADMIN'), ('ROLE_USER');

insert into user_roles (user_id, role_id) values (1, 1), (2, 2)