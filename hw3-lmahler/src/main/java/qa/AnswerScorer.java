package qa;

/*
 * The AnswerScorer loops through each Question (there should only be one in each document). For
 * each Question, the AnswerScorer loops through the candidate Answer annotations and scores them.
 * 
 * For this implementation, the scoring methodology is a simple NGram overlap rule: for each NGram
 * contained in the candidate Answer, the AnswerScorer looks to see if that NGram is also contained
 * in the Question. If so, it increments a “matching_ngrams” counter by 1. The final score feature
 * is simply the count of “matching_ngrams” / the total number of n-grams contained in the candidate
 * Answer.
 * 
 * When the score is returned, a new AnswerScore annotation is created. This annotation spans the
 * text of the answer sentence. In addition, it has a score feature (that stores the score), as well
 * as an answer feature (that has a pointer to the Answer object that was used during scoring).
 */

import java.util.HashMap;
import java.util.Map;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.*;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.cas.*;
import org.apache.uima.cas.text.*;
import org.cleartk.ne.type.NamedEntity;
import org.cleartk.ne.type.NamedEntityMention;
import org.uimafit.util.JCasUtil;

import edu.cmu.deiis.types.*;

/**
 * The AnswerScorer loops through each Question (there should only be one in each document). For
 * each Question, the AnswerScorer loops through the candidate Answer annotations and scores them.
 * 
 * For this implementation, the scoring methodology is a simple NGram overlap rule: for each NGram
 * contained in the candidate Answer, the AnswerScorer looks to see if that NGram is also contained
 * in the Question. If so, it increments a “matching_ngrams” counter by 1. The final score feature
 * is simply the count of “matching_ngrams” / the total number of n-grams contained in the candidate
 * Answer.
 * 
 * When the score is returned, a new AnswerScore annotation is created. This annotation spans the
 * text of the answer sentence. In addition, it has a score feature (that stores the score), as well
 * as an answer feature (that has a pointer to the Answer object that was used during scoring).
 */
public class AnswerScorer extends JCasAnnotator_ImplBase {

  // *************************************************************
  // * process *
  // *************************************************************
  public void process(JCas aJCas) throws AnalysisEngineProcessException {

    // Iterate over NamedEntity. NOTE: NamedEntity is not an annotation. Used NamedEntityMention instead
    //FSIterator<?> iter= aJCas.getJFSIndexRepository().getAllIndexedFS(NamedEntity.type);
    //while (iter.hasNext()) {
    //  NamedEntity ne= (NamedEntity) iter.next();
    //}
    
    // Store counts of Ngrams that appeared in the question
    Map<String, Integer> qngMap = new HashMap<String, Integer>();
    int endOfQuestionPos = 0;
    int matching_ngrams = 0;
    int total_ngrams = 0;

    // Within each Question, count the occurrence of each NGram
    AnnotationIndex qIndex = aJCas.getAnnotationIndex(Question.type);
    FSIterator<AnnotationFS> qIter = qIndex.iterator();
    while (qIter.hasNext()) {
      AnnotationFS q = (Question) qIter.next();
      endOfQuestionPos = Math.max(endOfQuestionPos, q.getEnd());

      AnnotationIndex qngIndex = aJCas.getAnnotationIndex(NGram.type);
      FSIterator<AnnotationFS> qngIter = qngIndex.subiterator(q);
      while (qngIter.hasNext()) {
        AnnotationFS qng = qngIter.next();
        String ngramText = qng.getCoveredText();
        int count = qngMap.containsKey(ngramText) ? qngMap.get(ngramText) : 0;
        qngMap.put(ngramText, count + 1);
      }
    }

    // Within each NamedEntityMention, count the occurrence of each NGram
    AnnotationIndex neIndex = aJCas.getAnnotationIndex(NamedEntityMention.type);
    FSIterator<AnnotationFS> neIter = neIndex.iterator();
    while (neIter.hasNext()) {
      AnnotationFS ne = (NamedEntityMention) neIter.next();
      if (ne.getEnd() <= endOfQuestionPos) {
        AnnotationIndex nengIndex = aJCas.getAnnotationIndex(NGram.type);
        FSIterator<AnnotationFS> nengIter = nengIndex.subiterator(ne);
        while (nengIter.hasNext()) {
          AnnotationFS neng = nengIter.next();
          String ngramText = neng.getCoveredText();
          int count = qngMap.containsKey(ngramText) ? qngMap.get(ngramText) : 0;
          qngMap.put(ngramText, count + 1);
        }
      } else {
        AnnotationIndex nengIndex2 = aJCas.getAnnotationIndex(NGram.type);
        FSIterator<AnnotationFS> nengIter2 = nengIndex2.subiterator(ne);
        while (nengIter2.hasNext()) {
          AnnotationFS neng2 = nengIter2.next();
          total_ngrams += 1;
          String ngramText = neng2.getCoveredText();
          if (qngMap.containsKey(ngramText)) {
            matching_ngrams += 1;
          }
        }        
      }
    }

    
    // Iterate over Answers and measure closeness
    AnnotationIndex aIndex = aJCas.getAnnotationIndex(Answer.type);
    FSIterator<AnnotationFS> aIter = aIndex.iterator();
    while (aIter.hasNext()) {
      AnnotationFS a = (Answer) aIter.next();

      // Within each Answer, count how many of its NGrams
      // exactly match NGrams in the Question
      AnnotationIndex angIndex = aJCas.getAnnotationIndex(NGram.type);
      FSIterator<AnnotationFS> angIter = angIndex.subiterator(a);
      while (angIter.hasNext()) {
        AnnotationFS ang = angIter.next();
        total_ngrams += 1;
        String ngramText = ang.getCoveredText();
        if (qngMap.containsKey(ngramText)) {
          matching_ngrams += 1;
        }
      }

      // Create AnswerScore and insert into index
      AnswerScore annotation = new AnswerScore(aJCas);
      annotation.setBegin(a.getBegin());
      annotation.setEnd(a.getEnd());
      annotation.setCasProcessorId("AnswerScorer");
      annotation.setScore(matching_ngrams / (double) total_ngrams);
      annotation.setConfidence(1);
      annotation.setAnswer((Answer) a);
      annotation.addToIndexes();

    }

  }
}
