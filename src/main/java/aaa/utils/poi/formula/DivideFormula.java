package aaa.utils.poi.formula;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class DivideFormula extends BaseFormula {

	protected static final String DIVIDE_FORMULA_NAME = "/"; //$NON-NLS-1$

	IBaseFormula dividend;
	IBaseFormula divider;

	@Override
	public String formatAsString() {
		return surroundWithBrackets(dividend.formatAsString()) + DIVIDE_FORMULA_NAME
			+ surroundWithBrackets(divider.formatAsString());
	}

}
