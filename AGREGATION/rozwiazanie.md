##Punkt 1

Do zadania wykorzystuje bazę z zadania EDA. Jest to Book_Corpus.

Przykładowy JSON z bazy:

db.train.find({word: "name"}).limit(1)

{ "_id" : ObjectId("564d006e2c60dab0b9d6ebe8"), "ID" : 52, "word" : "name", "line" : 5 }

##Punkt 2

#####Konwersja na plik csv została wykonan za pomocą programu BookToCSV zaimplementowanym w jezyku java. [Link do kodu](https://github.com/rjasinski/nosql/blob/master/EDA/BookToCSV.java)
Jego struktura to: ID - oznaczające nr wystąpienia słowa w tekście, word - zawiera słowo, line - zawierajaca nr lini w której było słowo.

Program wykorzystuje zbiór standardowych słów stopu dla języka angielskiego zapisanych w pliku [stopword.txt](https://github.com/rjasinski/nosql/blob/master/EDA/stopword.txt). Dodatkowo po wstępnym zajrzeniu do danych okaząło się że znaki interpunkcyjne bedą wykrywane jako słowa. Z tego powodu zostały za wczasu dodane do słów stopu.

#####Wczytanie do bazy poleceniem:

mongoimport -d fnsql -c train --type csv --file ~/EDA/book_large_p1.csv --headerline

##Punkt 3

#####Zliczamy ilość słów zaczynajacych sie na "a"

```js 
db.train.aggregate( [
  { s_word: { $exists: true}, $substr: [ "$word", 0, 1 ] },
  { $match: { s_word: "a" } },
  { ile: { $sum: 1} },
  { $limit: 1 }
]);
