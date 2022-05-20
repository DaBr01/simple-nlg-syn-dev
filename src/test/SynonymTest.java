/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Contributor(s): Daniel Braun, University of Twente.
 */


import org.junit.jupiter.api.Assertions;
import simplenlgde.framework.NLGElement;
import simplenlgde.framework.NLGFactory;
import simplenlgde.framework.WordElement;
import simplenlgde.lexicon.Lexicon;
import simplenlgde.lexicon.XMLLexicon;
import simplenlgde.phrasespec.AdjPhraseSpec;
import simplenlgde.phrasespec.NPPhraseSpec;
import simplenlgde.phrasespec.SPhraseSpec;
import simplenlgde.phrasespec.VPPhraseSpec;
import simplenlgde.realiser.Realiser;

import java.util.Arrays;
import java.util.List;

public class SynonymTest {
    private Lexicon lexicon;
    private NLGFactory nlgFactory;
    private Realiser realiser;

    public SynonymTest(Lexicon lexicon, NLGFactory nlgFactory, Realiser realiser) {
        this.lexicon = lexicon;
        this.nlgFactory = nlgFactory;
        this.realiser = realiser;
    }

    public void testSynonym1(){
        NPPhraseSpec np = nlgFactory.createNounPhrase("Auto");

        /** set list of synonyms **/
        ((WordElement) np.getNoun()).setSynonyms(new WordElement[]{lexicon.getWord("Kraftfahrzeug"), lexicon.getWord("Kutsche")});
        ((WordElement) np.getNoun()).setReplaceWithSynonym(true);

        np.setDeterminer("das");
        VPPhraseSpec vp = nlgFactory.createVerbPhrase("fahren");
        SPhraseSpec sentence = nlgFactory.createClause();
        sentence.setSubject(np);
        sentence.setVerb(vp);
        String output = realiser.realiseSentence(sentence);
        System.out.println(output);

        String expectedOutput[] = {"Das Auto fährt.", "Das Kraftfahrzeug fährt.", "Die Kutsche fährt."};
        List<String> expectedOutcomesList = Arrays.asList(expectedOutput);

        Assertions.assertTrue(expectedOutcomesList.contains(output));
    }

    public void testSynonym2(){
        NPPhraseSpec np = nlgFactory.createNounPhrase("Auto");
        np.setDeterminer("das");

        VPPhraseSpec vp = nlgFactory.createVerbPhrase("fahren");


        /** set list of synonyms **/
        ((WordElement) vp.getVerb()).setSynonyms(new WordElement[]{lexicon.getWord("schleichen")});
        ((WordElement) vp.getVerb()).setReplaceWithSynonym(true);

        SPhraseSpec sentence = nlgFactory.createClause();
        sentence.setSubject(np);
        sentence.setVerb(vp);
        String output = realiser.realiseSentence(sentence);
        System.out.println(output);

        String expectedOutput[] = {"Das Auto fährt.", "Das Auto schleicht."};
        List<String> expectedOutcomesList = Arrays.asList(expectedOutput);

        Assertions.assertTrue(expectedOutcomesList.contains(output));
    }

    public void testSynonym3(){
        NPPhraseSpec np = nlgFactory.createNounPhrase("Auto");
        np.setDeterminer("das");

        AdjPhraseSpec adj = nlgFactory.createAdjectivePhrase("schön");
        /** set list of synonyms **/
        ((WordElement) adj.getAdjective()).setSynonyms(new WordElement[]{lexicon.getWord("hübsch")});
        ((WordElement) adj.getAdjective()).setReplaceWithSynonym(true);

        np.addModifier(adj);

        VPPhraseSpec vp = nlgFactory.createVerbPhrase("fahren");

        SPhraseSpec sentence = nlgFactory.createClause();
        sentence.setSubject(np);
        sentence.setVerb(vp);
        String output = realiser.realiseSentence(sentence);
        System.out.println(output);

        String expectedOutput[] = {"Das schöne Auto fährt.", "Das hübsche Auto fährt."};
        List<String> expectedOutcomesList = Arrays.asList(expectedOutput);

        Assertions.assertTrue(expectedOutcomesList.contains(output));
    }

}