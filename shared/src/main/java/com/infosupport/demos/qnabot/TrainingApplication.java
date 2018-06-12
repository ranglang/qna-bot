package com.infosupport.demos.qnabot;

import org.deeplearning4j.ui.api.UIServer;

import java.io.File;
import java.util.Map;

public class TrainingApplication {
    public static void main(String... args) throws Exception {
        UIServer server = UIServer.getInstance();

        QuestionVectorizer vectorizer = QuestionVectorizerFactory.create(new File("data/"));
        Map<Integer, String> answers = AnswersMappingFactory.create(new File("data/answers.csv"));

        vectorizer.fit();

        QuestionClassifier classifier = QuestionClassifierFactory.create(vectorizer, answers);

        server.attach(classifier.getStatsStorage());

        classifier.fit(new File("data/questions_train.csv"));

        double score = classifier.score(new File("data/questions_train.csv"));

        System.out.println(String.format("Accuracy: %f",score));

        classifier.save(new File("model/classifier.bin"));
    }
}