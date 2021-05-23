INSERT INTO TB_BASE_USER (email, name) VALUES ('sac@exposicaocarrosantigos.br', 'Roberto Carlos');
INSERT INTO TB_BASE_USER (email, name) VALUES ('sac@showbaroes.br', 'Maria Jose');
INSERT INTO TB_BASE_USER (email, name) VALUES ('sac@balonismoboituva.br', 'Pedro Silva');
INSERT INTO TB_BASE_USER (email, name) VALUES ('giseleoliveira@outlook.br', 'Gisele');
INSERT INTO TB_BASE_USER (email, name) VALUES ('liviacristina@gmail.br', 'Livia');
INSERT INTO TB_BASE_USER (email, name) VALUES ('aloisiocarlos@gmail.br', 'Aloisio');

INSERT INTO TB_ADMIN (phone_number, base_user_id) VALUES ('15999999991', 1);
INSERT INTO TB_ADMIN (phone_number, base_user_id) VALUES ('15999999992', 2);
INSERT INTO TB_ADMIN (phone_number, base_user_id) VALUES ('15999999993', 3);

INSERT INTO TB_ATTEND (balance, attend_baseuser_id) VALUES (50.00, 4);
INSERT INTO TB_ATTEND (balance, attend_baseuser_id) VALUES (100.00, 5);
INSERT INTO TB_ATTEND (balance, attend_baseuser_id) VALUES (10.00, 6);

INSERT INTO TB_PLACE (address, name) VALUES ('Rua 31 de Março, Votorantim', 'Praça de eventos');
INSERT INTO TB_PLACE (address, name) VALUES ('Av. Dom Aguirre, Sorocaba', 'Parque das Águas');
INSERT INTO TB_PLACE (address, name) VALUES ('Rua dos balões, Boituva', 'Centro de balonismo');

INSERT INTO TB_EVENT (name, description, start_date, end_date, start_time, end_time, email_contact, amount_paid_tickets, amount_free_tickets, ticket_price, admin_user_id) VALUES ('Exposição de carros antigos', 'Maior exposição de carros antigos da regiao.', '2021-06-10', '2021-06-20', '08:00:00', '16:00:00', 'prefeituravotorantim@outlook.com', 80, 20, 20.00 ,1);
INSERT INTO TB_EVENT (name, description, start_date, end_date, start_time, end_time, email_contact, amount_paid_tickets, amount_free_tickets, ticket_price, admin_user_id) VALUES ('Show Barões da Pisadinha', 'Barões da Pisadinha Ao Vivo.', '2021-09-16', '2021-09-16', '16:00:00', '20:00:00', 'prefeiturasorocaba@outlook.com', 760, 500, 25, 2);
INSERT INTO TB_EVENT (name, description, start_date, end_date, start_time, end_time, email_contact, amount_paid_tickets, amount_free_tickets, ticket_price, admin_user_id) VALUES ('Festival de Balões', 'Venha prestigiar os maiores baloes da região.', '2021-11-21', '2021-11-22', '09:00:00', '15:00:00', 'prefeituraboituva@outlook.com', 760, 500, 25, 3);