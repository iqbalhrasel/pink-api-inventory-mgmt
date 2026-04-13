create table users(
    id int AUTO_INCREMENT,
    name varchar(255) not null,
    username varchar(255) not null unique,
    password varchar(255) not null,
    role varchar(255) not null,
    active boolean not null,
    primary key(id)
);

create table ref_tokens(
    id int AUTO_INCREMENT,
    user_id int not null,
    token varchar(255) not null,
    primary key(id)
);

create table sizes(
    id int AUTO_INCREMENT,
    name varchar(255) not null,
    primary key(id)
);

create table categories(
    id int AUTO_INCREMENT,
    name varchar(255) not null,
    primary key(id)
);

create table products(
    id int AUTO_INCREMENT,
    category_id int not null,
    name varchar(255) not null,
    type varchar(255) not null,
    primary key(id),
    foreign key(category_id) references categories(id)
);

create table product_sizes(
    id int AUTO_INCREMENT,
    product_id int not null,
    size_id int not null,
    code varchar(255) not null,
    quantity int not null,
    buying_price decimal(10,2) default 0,
    selling_price decimal(10,2) default 0,
    primary key(id),
    foreign key(product_id) references products(id),
    foreign key(size_id) references sizes(id)
);

create table stock_logs(
    id int AUTO_INCREMENT,
    imported_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    product_id int not null,
    size_id int not null,
    code varchar(255) not null,
    quantity int not null,
    buying_price decimal(10,2) default 0,
    selling_price decimal(10,2) default 0,
    approval_status varchar(255) not null,
    primary key(id),
    foreign key(product_id) references products(id),
    foreign key(size_id) references sizes(id)
);

create table sales(
    id int AUTO_INCREMENT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    discount decimal(5, 2) not null,
    total_amount decimal(10,2) not null,
    location_type varchar(255) not null,
    shopping_type varchar(255) not null,
    delivery_vendor varchar(255),
    username varchar(255) not null,
    primary key(id)
);

create table sale_products(
    id int AUTO_INCREMENT,
    sale_id int not null,
    product_id int not null,
    product_size_id int not null,
    quantity int not null,
    buying_price decimal(10,2) not null,
    selling_price decimal(10,2) not null,
    primary key(id),
    foreign key(sale_id) references sales(id),
    foreign key(product_id) references products(id),
    foreign key(product_size_id) references product_sizes(id)
);

create table customers(
    id int AUTO_INCREMENT,
    sale_id int not null unique,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    name varchar(255) not null,
    phone varchar(255) not null,
    shopping_type varchar(255) not null,
    address varchar(255),
    remarks varchar(255),
    primary key(id),
    foreign key(sale_id) references sales(id)
);

DELIMITER $$

CREATE PROCEDURE get_sales_report(IN from_date DATETIME, IN to_date DATETIME)
BEGIN
    select
        s.id as id,
        s.created_at as created_at,
        c.name as customer_name,
        c.phone as customer_phone,
        sum(sp.quantity) as total_items,
    	sum(sp.buying_price * sp.quantity) as total_buying_price,
    	s.discount as discount,
    	s.location_type as location_type,
    	s.shopping_type as shopping_type,
    	s.username as username,
    	s.total_amount as total_amount
    from sales s
    join sale_products sp on s.id=sp.sale_id
    join customers c on s.id =c.sale_id
    where s.created_at between from_date and to_date
    group by s.id
    order by s.created_at desc;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE get_sales_report_by_type(IN shop_type varchar(255), IN from_date DATETIME, IN to_date DATETIME)
BEGIN
    select
        s.id as id,
        s.created_at as created_at,
        c.name as customer_name,
        c.phone as customer_phone,
        sum(sp.quantity) as total_items,
    	sum(sp.buying_price * sp.quantity) as total_buying_price,
    	s.discount as discount,
    	s.location_type as location_type,
    	s.shopping_type as shopping_type,
    	s.username as username,
    	s.total_amount as total_amount
    from sales s
    join sale_products sp on s.id=sp.sale_id
    join customers c on s.id =c.sale_id
    where s.shopping_type = shop_type and s.created_at between from_date and to_date
    group by s.id
    order by s.created_at desc;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE get_customer_purchase_history(IN sales_id int)
BEGIN
    select
    	p.name as product_name,
    	s.name as size_name,
    	sp.quantity as quantity
    from sale_products sp
    join products p on sp.product_id =p.id
    join product_sizes ps on sp.product_size_id =ps.id
    join sizes s on ps.size_id =s.id
    where sp.sale_id =sales_id;
END $$

DELIMITER ;
