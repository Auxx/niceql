NiceQL
======

NiceQL is a very small wrapper library to make your SQLite tasks on Android
much easier. Google have made a lot of useful APIs to make different queries,
escape values, use place holders etc, but establishing new database connection
requires some dirty work. Hard-coded SQL to create tables anyone?

This is where NiceQL comes to the rescue! It will give you a set of simple,
easy to use, yet powerful tools to fully manage your database schemes,
tables inside, their columns, indices and seeds.

With this library you can programmatically create scheme from scratch,
read it from XML Android resource or file or even hard-code some SQL if
you like.   

## Bits and pieces

NiceQL is made of three main parts:

1. **Interfaces** - everyone loves interfaces in OOP!
2. **Structures** - basic building blocks of your database, like Column, Table etc.
3. **Helpers** - Small wrappers to work with database directly.

Check out project wiki for documentation and examples.