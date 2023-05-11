-- Add sample users
-- On prod this does not belong in a migration
insert into users (id, first_name, last_name)
values (1, 'Brad', 'Pitt'),
       (2, 'Gwyneth', 'Paltrow'),
       (3, 'Morgan', 'Freeman');
