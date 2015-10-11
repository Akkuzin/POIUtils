package aaa.utils.poi.formula;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import aaa.utils.poi.FormulaUtils;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RangeFormula extends BaseFormula {

	protected static final String RANGE_FORMULA_DELIMITER = ":"; //$NON-NLS-1$

	String range;

	public RangeFormula(String cellStart) {
		this.range = cellStart;
	}

	public RangeFormula(String cellStart, String cellFinish) {
		this.range = cellStart + RANGE_FORMULA_DELIMITER + cellFinish;
	}

	public RangeFormula(long rowStart, long columnStart) {
		this(FormulaUtils.getCellAddress(rowStart, columnStart));
	}

	public RangeFormula(long rowStart, long columnStart, long rowFinish, long columnFinish) {
		this(	FormulaUtils.getCellAddress(rowStart, columnStart),
				FormulaUtils.getCellAddress(rowFinish, columnFinish));
	}

	@Override
	public String formatAsString() {
		return range;
	}

}
