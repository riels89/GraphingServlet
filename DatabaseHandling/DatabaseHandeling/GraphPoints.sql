create table if not exists graph_normal(id int);
select * from graph_normal
drop table graph_normal 

delete from graph_normal where id = 1

drop table graph_normal ;
drop table graph_normal_images;

create table graph_normal_images
(
image_id int primary key auto_increment,
path varchar(100)
);

select path from graph_normal_images
select * from graph_normal
if not exists()
insert into images
(path) values ("empty")

SET FOREIGN_KEY_CHECKS=1