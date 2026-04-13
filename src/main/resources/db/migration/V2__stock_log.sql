DELIMITER $$

CREATE PROCEDURE get_stock_logs(IN from_date DATETIME, IN to_date DATETIME)
BEGIN
    select
    	sl.id as id,
    	sl.imported_at as imported_at,
    	p.name as product_name,
    	s.name as size_name,
    	sl.code as code,
    	sl.quantity as quantity,
    	sl.buying_price as buying_price,
    	sl.selling_price as selling_price
    from stock_logs sl
    join products p on sl.product_id =p.id
    join sizes s on sl.size_id =s.id
    where sl.imported_at BETWEEN from_date and to_date
    order by sl.imported_at desc;
END $$

DELIMITER ;