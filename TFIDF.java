//現在二つのファイルのみ
//試作TFIDF
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TFIDF {
	static String [] file_name;
	public static void Tfidf(String[] name){
		List<String> documents = new ArrayList<String>();
		//file存在判定用のフラグ
		int flag = 0;
		// ファイル読み込み
		try {
			// ファイルのパスを指定する
			//ファイル名を自分で入力 テストケースを使用する場合はQを入力
			////////////////////////////////////////////////////////////////////////////////////
			// System.out.println("ファイル名を入力してください testfile: enter \'Q test\' ");
			// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			// String buf = null;
			// buf = br.readLine();
			// file_name = buf.split("\\s");
			///////////////////////////////////////////////////////////////////////////////////
			//ファイルが二つでない場合のエラー処理
			// if(file_name.length != 2){
			// 	System.out.println("ファイル名を二つ入力してください");
			// 	return;
			// }
			file_name = name;
			File [] file;

			if(file_name[0].equals("Q")){
				file_name = new String[3];
				file_name[0] = "test_a.txt";
				file_name[1] = "test_b.txt";
				file_name[2] =  "test_c.txt";
				file = new File[file_name.length];
				for(int i = 0; i<file_name.length;i++){
					file[i] = new File(file_name[i]);
				}

			}else{
				file = new File[file_name.length];
				for(int i = 0; i<file_name.length; i++){
					file[i] = new File(file_name[i]);
				}
			}
			// ファイルが存在しない場合に例外が発生するので確認する
			for(int i = 0; i<file_name.length;i++){
				if (!file[i].exists() ) {
					System.out.println(file[i]+"というファイルは存在しません");
					flag = 1;
				}
			}
			if(flag == 1)return;
			// BufferedReaderクラスのreadLineメソッドを使って1行ずつ読み込み表示する
			for(int i = 0 ; i<file_name.length; i++){
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file[i]));
				String data;
				while ((data = bufferedReader.readLine()) != null) {
					documents.add(data);
					System.out.println(data);
				}
			}
			// 最後にファイルを閉じてリソースを開放する
			// br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		//ファイル読み込み終了
		FeatureVectorGeneratorE generator = new FeatureVectorGeneratorE();
		Map<String, double[]> featureVectors = generator.generateTFIDFVectors(documents);
		// 二次元リストのためのリストと、ベクトルの絶対値を保存していくリストを宣言
		ArrayList<Double> absolute_vector = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> vector_2d = new ArrayList<ArrayList<Double>>();

		int count = 1;
		for(Map.Entry<String, double[]> entry : featureVectors.entrySet()){
			//ベクトルの絶対値を求める際の全ての要素を２乗和を入れる値の宣言、逐次初期化するためにループ内で宣言
			double sum_vector = 0.0;
			ArrayList<Double> vector_1d = new ArrayList<Double>();
			System.out.println("--- Document"+count+ "---");
			count++;
			System.out.println(entry.getKey());

			System.out.println("--- Feature Vector ---");
			double[] featureVector = entry.getValue();

			System.out.print("(");
			for(int i=0; i < featureVector.length; i++){
				System.out.print(String.format("%.2f", featureVector[i]));
				// 二乗にした要素を入れていく
				sum_vector += Math.pow(featureVector[i],2);
				//ベクトルのリストのリストを作るための一行それぞれのベクトルの要素を格納するリスト
				vector_1d.add(featureVector[i]);

				if(i != featureVector.length-1){
					System.out.print(", ");
				}
			}
			// 一行のベクトルのリストをリストにしていく(二次元リスト)
			vector_2d.add(vector_1d);
			//一行ごとのベクトルの絶対値を求めそれをリストに入れていく
			absolute_vector.add(Math.sqrt(sum_vector));

			System.out.println(")");
			System.out.println("");
		}

		System.out.println("---cossimilarity---");

		// Calcluatecos cos  = new Calculatecos(vector_2d, absolute_vector);
		// cos.calcu();

		double cos = 0.0;
		double inner = 0.0;
		for(int k = 0;k<vector_2d.size();k++){
			int i;
			for(i = k+1; i<vector_2d.size();i++){
				inner = 0.0;
				for(int j =0; j<vector_2d.get(0).size();j++){
					if( i != k ){inner += vector_2d.get(i).get(j)*vector_2d.get(k).get(j);}
				}
				if(i !=k){cos = inner/(absolute_vector.get(i)*absolute_vector.get(k));}
				System.out.println("document"+(k+1)+" vs "+ "document"+(i+1) +" cos_theta: "+cos);
			}
		}
	}
}
