package br.com.italoromeiro.arctouchtest;

import org.junit.Test;

import br.com.italoromeiro.arctouchtest.utils.FormatterUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by italo on 12/08/16.
 */
public class FormatterUtilTest {
    private String toCompare = "italo romeiro wanderley";

    @Test
    public void withSpacesAtTheBeginning() throws Exception {
        String toTest = " italo romeiro wanderley";
        toTest = FormatterUtil.cleanString(toTest);
        assertThat(toCompare, is(toTest));
    }

    @Test
    public void withSpacesAtTheMiddle() throws Exception {
        String toTest = "italo  romeiro   wanderley";
        toTest = FormatterUtil.cleanString(toTest);
        assertThat(toCompare, is(toTest));
    }

    @Test
    public void withSpacesAtTheEnd() throws Exception {
        String toTest = "italo romeiro wanderley ";
        toTest = FormatterUtil.cleanString(toTest);
        assertThat(toCompare, is(toTest));
    }

    @Test
    public void withSpacesAtAllParts() throws Exception {
        String toTest = " italo  romeiro    wanderley    ";
        toTest = FormatterUtil.cleanString(toTest);
        assertThat(toCompare, is(toTest));
    }

    @Test
    public void nullString() throws Exception {
        String toTest = null;
        toTest = FormatterUtil.cleanString(toTest);
        assertThat("", is(toTest));
    }

    @Test
    public void justSpacesString() throws Exception {
        String toTest = "  ";
        toTest = FormatterUtil.cleanString(toTest);
        assertThat("", is(toTest));
    }
}
