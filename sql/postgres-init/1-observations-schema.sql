create table observations(
    id int generated always as identity,
    description text not null,
    notes text[] not null
);
