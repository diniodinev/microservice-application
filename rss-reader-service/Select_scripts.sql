SELECT Author_name, Count(Author_name) FROM "COMMENT" group by  "PUBLIC"."COMMENT".Author_name order by Count(Author_name)  desc;
SELECT * FROM "COMMENT" where Author_name='Алов'
SELECT * FROM "COMMENT"  order by DISLIKES  desc;
SELECT * FROM "COMMENT"  order by LIKES  desc;