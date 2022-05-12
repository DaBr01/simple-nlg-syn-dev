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


import simplenlgde.framework.NLGElement;
import simplenlgde.framework.NLGFactory;
import simplenlgde.framework.WordElement;
import simplenlgde.lexicon.Lexicon;
import simplenlgde.lexicon.XMLLexicon;
import simplenlgde.phrasespec.NPPhraseSpec;
import simplenlgde.phrasespec.SPhraseSpec;
import simplenlgde.phrasespec.VPPhraseSpec;
import simplenlgde.realiser.Realiser;

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
        System.out.println(realiser.realiseSentence(sentence));

        String output = "";
    }

}