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
			System.out.println("ファイル名を入力してください testfile: enter \'Q\' ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String buf = null;
			buf = br.readLine();
			File file = new File("");
			if(buf.equals("Q")){
				file = new File("Test.txt");
			}else{
				file = new File(buf);
			}
			// ファイルが存在しない場合に例外が発生するので確認する
			if (!file.exists()) {
				System.out.println("ファイルが存在しません");
				return;
			}
			// BufferedReaderクラスのreadLineメソッドを使って1行ずつ読み込み表示する
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String data;
			while ((data = bufferedReader.readLine()) != null) {
				documents.add(data);
				System.out.println(data);
			}
			// 最後にファイルを閉じてリソースを開放する
			bufferedReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		//ファイル読み込み終了
		FeatureVectorGeneratorE generator = new FeatureVectorGeneratorE();
		Map<String, double[]> featureVectors = generator.generateTFIDFVectors(documents);

		ArrayList<Double> absolute_vector = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> vector_2d = new ArrayList<ArrayList<Double>>();


		for(Map.Entry<String, double[]> entry : featureVectors.entrySet()){
			double sum_vector = 0.0;
			ArrayList<Double> vector_1d = new ArrayList<Double>();
			System.out.println("--- Document ---");
			System.out.println(entry.getKey());

			System.out.println("--- Feature Vector ---");

			double[] featureVector = entry.getValue();
			System.out.print("(");
			for(int i=0; i < featureVector.length; i++){
				System.out.print(String.format("%.2f", featureVector[i]));
				sum_vector += Math.pow(featureVector[i],2);
				vector_1d.add(featureVector[i]);

				if(i != featureVector.length-1){
					System.out.print(", ");
				}
			}
			vector_2d.add(vector_1d);
			absolute_vector.add(Math.sqrt(sum_vector));

			System.out.println(")");
			System.out.println("");
		}
		System.out.println("---cossimilarity---");

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
