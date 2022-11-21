
    create table items (
       order_number bigint not null auto_increment,
        customer_name varchar(50) not null,
        due_date date not null,
        order_type varchar(255),
        primary key (order_number)
    ) engine=InnoDB;
