INSERT INTO TT_USER (NAME, AGE) VALUES ('OHOgdfthI', '100');
commit;
rollback;

select * from TT_USER;

delete from TT_USER WHERE NAME not in ( 'CCC', 'AAA', 'BBB', 'DDD');
commit;

update TT_USER set AGE = '300' WHERE NAME in ('AAA');
commit;
update TT_USER set AGE = '300' WHERE NAME in ('CCC');
commit;

update TT_USER set AGE = '100';