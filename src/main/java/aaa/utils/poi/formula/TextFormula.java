package aaa.utils.poi.formula;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class TextFormula extends BaseFormula {

	protected static final String TEXT_FORMULA_NAME = "TEXT"; //$NON-NLS-1$
	protected static final String TEXT_FORMULA_DELIMITER = ","; //$NON-NLS-1$

	IBaseFormula value;
	String format;

	@Override
	public String formatAsString() {
		return TEXT_FORMULA_NAME
			+ surroundWithBrackets(value.formatAsString() + TEXT_FORMULA_DELIMITER + format);
	}

}
