package br.com.italoromeiro.arctouchtest;

import org.junit.Test;

import br.com.italoromeiro.arctouchtest.utils.FormatterUtil;

import static org.junit.Assert.assertEquals;

/**
 * Created by italo on 12/08/16.
 */
public class FormatterUtilTest {
    private String toCompare = "italo romeiro wanderley";

    @Test
    public void withSpacesAtTheBeginning() throws Exception {
        String toTest = " italo romeiro wanderley";
        toTest = FormatterUtil.cleanString(toTest);
        assertEquals("Strings must be the same", toCompare, toTest);
    }

    @Test
    public void withSpacesAtTheMiddle() throws Exception {
        String toTest = "italo  romeiro   wanderley";
        toTest = FormatterUtil.cleanString(toTest);
        assertEquals("Strings must be the same", toCompare, toTest);
    }

    @Test
    public void withSpacesAtTheEnd() throws Exception {
        String toTest = "italo romeiro wanderley ";
        toTest = FormatterUtil.cleanString(toTest);
        assertEquals("Strings must be the same", toCompare, toTest);
    }

    @Test
    public void withSpacesAtAllParts() throws Exception {
        String toTest = " italo  romeiro    wanderley    ";
        toTest = FormatterUtil.cleanString(toTest);
        assertEquals("Strings must be the same", toCompare, toTest);
    }
}
