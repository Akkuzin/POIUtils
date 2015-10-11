package aaa.utils.poi.formula;

public abstract class BaseFormula implements IBaseFormula {

	protected static final String OPEN_BRACKET = "("; //$NON-NLS-1$
	protected static final String CLOSE_BRACKET = ")"; //$NON-NLS-1$

	protected static String surroundWithBrackets(String value) {
		return OPEN_BRACKET + value + CLOSE_BRACKET;
	}

}
