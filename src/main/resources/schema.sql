create table if not exists clothe (
   id               identity,
   name             varchar(40) not null,
   price            double not null,
   year_of_creation int not null,
   brand            varchar(20) not null
);