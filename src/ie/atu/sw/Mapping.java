package ie.atu.sw;

import java.util.Map;

import java.util.concurrent.ConcurrentSkipListMap;


public class Mapping{

	private Map<String, Double> lexiconMap;
	private FileProcessor Fprocess = new FileProcessor();
	
	private Runner load;
	private volatile double positiveScore, negativeScore, totalWords;

	public Mapping() {
		lexiconMap = new  ConcurrentSkipListMap<>();
		
	}


	public void filetoMap(String filename) throws Exception{
		Fprocess.processFile(filename, this::toMap);
	}


	public String getSentiment(String filename) throws Exception{ 
		Fprocess.processFile(filename, this::sentimentfromTweet);
		
		return calculateSentiment();
	}

    private String calculateSentiment() {
        double sentiment = ((positiveScore - negativeScore) / totalWords);
        if (sentiment > 0) {
            return "\nThe overall Sentiment is: Positive :), " + sentiment;
        } else if (sentiment < 0) {
            return "\nThe overall Sentiment is: Negative :( " + sentiment;
        } else {
            return "\nThe overall Sentiment is: Neutral :|" + sentiment;
        }
    }


	private synchronized void toMap(String line) {
		String mapData[] = line.split(",");
		String key = mapData[0];
		double value = Double.parseDouble(mapData[1]);
		lexiconMap.put(key, value);
	}

	private synchronized void sentimentfromTweet(String line){

		line = line.trim().replaceAll("[^a-zA-Z]", " ").toLowerCase();
		String[] words = line.split("\\s+");

		for (String word : words) {
			int index = java.util.Arrays.asList(words).indexOf(word);
			load.printProgress(index + 1, words.length);
			lexiconMap.forEach((key, value) ->{
				
				if(word.equals(key)) {
					
					synchronized(this) {
						if(value < 0) {
							negativeScore += value;
						}
						else if(value > 0) {
							positiveScore += value;
						}
						totalWords++;
					}

				}

			});
		}

	}



	//debugging
	public void showMap() {
		lexiconMap.forEach((key, value) -> {
			System.out.println("Key: " + key + ", Value: " + value);
		});
	}

}
