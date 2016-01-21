##Punkt 1

Do zadania wykorzystuje bazę z zadania EDA. Jest to Book_Corpus.

Przykładowy JSON z bazy:

```js
db.train.find({word: "name"}).limit(1)
{ 
   "_id" : ObjectId("564d006e2c60dab0b9d6ebe8"),
   "ID" : 52,
   "word" : "name",
   "line" : 5
}
```

##Punkt 2

#####Konwersja na plik csv została wykonan za pomocą programu BookToCSV zaimplementowanym w jezyku java. [Link do kodu](https://github.com/rjasinski/nosql/blob/master/EDA/BookToCSV.java)
Jego struktura to: ID - oznaczające nr wystąpienia słowa w tekście, word - zawiera słowo, line - zawierajaca nr lini w której było słowo.

Program wykorzystuje zbiór standardowych słów stopu dla języka angielskiego zapisanych w pliku [stopword.txt](https://github.com/rjasinski/nosql/blob/master/EDA/stopword.txt). Dodatkowo po wstępnym zajrzeniu do danych okaząło się że znaki interpunkcyjne bedą wykrywane jako słowa. Z tego powodu zostały za wczasu dodane do słów stopu.

#####Wczytanie do bazy poleceniem:

```sh
mongoimport -d fnsql -c train --type csv --file ~/EDA/book_large_p1.csv --headerline
```

##Punkt 3

Zliczamy ilość wystąpień słów zaczynajacych sie na samogłoskę

```js 
db.train.aggregate([
  { $project: {ID: 1 , word: 1 , line: 1 , firstWord: {$toLower: {$substr: ["$word", 0, 1 ]}}} },
  { $match: {$or: [{firsWord: "a"}, {firstWord: "e"}, {firstWord: "o"},
  {firstWord: "u"}, {firstWord: "y"}]} },
  { $group: {_id: "$firstWord", count: {$sum: 1}} },
  { $sort: {count: -1} }
]);
```
Czas wykonywania około 25 minut. Wynik:

| litera | liczba słów |
|--------|-------------|
| e | 8421592 |
| o | 4772552 |
| u | 2485326 |
| y | 1664233 |
