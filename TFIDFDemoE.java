import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TFIDFDemoE {
	public static void main(String[] args){
		List<String> documents = new ArrayList<String>();
		// ファイル読み込み
		try {
			// ファイルのパスを指定する
			//ファイル名を自分で入力 テストケースを使用する場合はQを入力
			System.out.println("ファイル名を入力してください testfile: enter \'Q test\' ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String buf = null;
			buf = br.readLine();
			String  [] file_name = buf.split("\\s");

			//ファイルが二つでない場合のエラー処理
			if(file_name.length != 2){
				System.out.println("ファイル名を二つ入力してください");
				return;
			}

			File [] file = {new File(""),new File("")};

			if(file_name[0].equals("Q")){
				file[0] = new File("test1.txt");
				file[1] = new File("test2.txt");
			}else{
				file[0] = new File(file_name[0]);
				file[1] = new File(file_name[1]);
			}
			// ファイルが存在しない場合に例外が発生するので確認する
			if (!file[0].exists() && !file[1].exists()) {
				System.out.println("ファイルは存在しません");
				return;
			}
			else if (!file[0].exists() ) {
				System.out.println(file[0]+"というファイルは存在しません");
				return;
			}else if( !file[1].exists() ){
				System.out.println(file[1]+"というファイルは存在しません");
				return;
			}
			// BufferedReaderクラスのreadLineメソッドを使って1行ずつ読み込み表示する
			for(int i = 0 ; i<file_name.length; i++){
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file[i]));
			String data;
			while ((data = bufferedReader.readLine()) != null) {
				documents.add(data);
				System.out.println(data);
			}
		}
			// BufferedReader = new BufferedReader(new FileReader(file[1]));
			// while ((data = bufferedReader.readLine()) != null) {
			// 	documents.add(data);
			// 	System.out.println(data);
			// }
			// 最後にファイルを閉じてリソースを開放する
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		//ファイル読み込み終了
		FeatureVectorGeneratorE generator = new FeatureVectorGeneratorE();
		Map<String, double[]> featureVectors = generator.generateTFIDFVectors(documents);
		// 二次元リストのためのリストと、ベクトルの絶対値を保存していくリストを宣言
		ArrayList<Double> absolute_vector = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> vector_2d = new ArrayList<ArrayList<Double>>();


		for(Map.Entry<String, double[]> entry : featureVectors.entrySet()){
			//ベクトルの絶対値を求める際の全ての要素を２乗和を入れる値の宣言、逐次初期化するためにループ内で宣言
			double sum_vector = 0.0;
			ArrayList<Double> vector_1d = new ArrayList<Double>();
			System.out.println("--- Document ---");
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
