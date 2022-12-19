----------------indexes--------------------
set autotrace on explain;

create index authors_i on authors(name, info);
drop index authors_i;

set autotrace off;
alter index index_name rebuild;
