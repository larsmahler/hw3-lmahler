/*
 * The Evaluator evaluates the performance of the AAE by comparing the system outputs
 * (AnswerScore.score values) with the gold standard outputs. It measures precision by doing the
 * following: 1) Ranks the Answers according to AnswerScore.score (descending). 2) Selects the top N
 * Answers (where N = the total number of correct answers that were possible for that Question). 3)
 * Measures precision by computing how many of the top N ranked Answers were actually correct (based
 * upon the gold standard).
 * 
 * After performing these calculations, it prints the results to screen.
 */

package qa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.CasToInlineXml;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.AnswerScore;

/**
 * The Evaluator evaluates the performance of the AAE by comparing the system outputs
 * (AnswerScore.score values) with the gold standard outputs. It measures precision by doing the
 * following: 1) Ranks the Answers according to AnswerScore.score (descending). 2) Selects the top N
 * Answers (where N = the total number of correct answers that were possible for that Question). 3)
 * Measures precision by computing how many of the top N ranked Answers were actually correct (based
 * upon the gold standard).
 * 
 * After performing these calculations, it prints the results to screen.
 */
public class EvaluatorCasConsumer extends CasConsumer_ImplBase {

  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas aJCas = null;
    try {
      aJCas = aCAS.getJCas();
    } catch (CASException e) {
      e.printStackTrace();
    }
    
    List<AnswerScore> asList = new ArrayList<AnswerScore>();

    // Iterate over AnswerScore annotations
    int num_correct_total = 0;
    AnnotationIndex asIndex = aJCas.getAnnotationIndex(AnswerScore.type);
    FSIterator<AnnotationFS> asIter = asIndex.iterator();
    while (asIter.hasNext()) {
      AnswerScore as = (AnswerScore) asIter.next();
      asList.add((AnswerScore) as);
      Answer a = (Answer) as.getAnswer();
      if (a.getIsCorrect()) {
        num_correct_total += 1;
      }
    }

    Collections.sort(asList, new Comparator<AnswerScore>() {
      @Override
      public int compare(AnswerScore o1, AnswerScore o2) {
        return new Double(o2.getScore()).compareTo(new Double(o1.getScore()));
      }
    });

    ListIterator<AnswerScore> asIter2 = asList.listIterator();
    int len = asList.size();
    int num_correct_in_top_n = 0;
    int num_total = 0;
    while (0 < len-- && asIter2.hasNext()) {
      num_total += 1;
      AnswerScore as = (AnswerScore) asIter2.next();
      Answer a = (Answer) as.getAnswer();
      if (a.getIsCorrect()) {
        System.out.print("+ ");
        if (num_total <= num_correct_total) {
          num_correct_in_top_n += 1;
        }
      } else {
        System.out.print("- ");
      }
      System.out.printf("%.2f ", as.getScore());
      System.out.println(as.getCoveredText());
    }

    System.out.printf("\nPrecision at %d: ", num_correct_total);
    double result = num_correct_in_top_n / (double) num_correct_total;
    System.out.printf("%.2f \n\n\n", result);  
    
  }

}
