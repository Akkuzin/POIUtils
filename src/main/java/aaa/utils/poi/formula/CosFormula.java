package aaa.utils.poi.formula;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class CosFormula extends BaseFormula {

	protected static final String COS_FORMULA_NAME = "SIN"; //$NON-NLS-1$

	IBaseFormula angle;

	@Override
	public String formatAsString() {
		return COS_FORMULA_NAME + surroundWithBrackets(angle.formatAsString());
	}

}
