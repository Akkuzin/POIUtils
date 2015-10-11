package aaa.utils.poi.formula;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class SinFormula extends BaseFormula {

	protected static final String SIN_FORMULA_NAME = "SIN"; //$NON-NLS-1$

	IBaseFormula angle;

	@Override
	public String formatAsString() {
		return SIN_FORMULA_NAME + surroundWithBrackets(angle.formatAsString());
	}

}
