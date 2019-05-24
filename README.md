# java_text_mining


get_htmlの変更後複数のURLを指定しファイルに書き込む

Extract.javaを用いてparseする。

#実行方法

javac -cp .:jsoup-1.8.2.jar Extract.java

javac -cp .:jsoup-1.8.2.jar Cosine_Similarity.java

java -cp .:jsoup-1.8.2.jar Cosine_Similarity



必要ファイル TFIDF.java Extract.java FeatureVectorGenerator.java


#sport business business testURL

#入力例(3つのwebページの場合)

->比較したい文書のURLを入力をしてください(複数可)

$ https://www.bbc.com/news/world-us-canada-44669009 https://www.bbc.com/news/live/business-44657989 https://www.bbc.com/news/business-44493414

->次に出力するファイル名をそれぞれ対応する順番で入力してください

$ document1.txt document2.txt document3.txt




#test URL

http://www.bbc.com/travel/story/20180701-the-five-countries-that-set-world-culture http://www.bbc.com/travel/story/20180703-why-no-one-speaks-indonesias-language https://www.bbc.com/sport/live/football/44071195
