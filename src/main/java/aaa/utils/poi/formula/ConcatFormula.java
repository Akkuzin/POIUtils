package aaa.utils.poi.formula;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ConcatFormula extends BaseFormula {

	protected static final String CONCAT_FORMULA_NAME = "&"; //$NON-NLS-1$

	IBaseFormula firstPart;
	IBaseFormula secondPart;

	@Override
	public String formatAsString() {
		return surroundWithBrackets(firstPart.formatAsString()) + CONCAT_FORMULA_NAME
			+ surroundWithBrackets(secondPart.formatAsString());
	}

}
