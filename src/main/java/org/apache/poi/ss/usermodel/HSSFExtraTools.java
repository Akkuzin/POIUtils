package org.apache.poi.ss.usermodel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;

public class HSSFExtraTools {

	private static void copyAttributes(Font font, AttributedString str, int startIdx, int endIdx) {
		str.addAttribute(TextAttribute.FAMILY, font.getFontName(), startIdx, endIdx);
		str.addAttribute(TextAttribute.SIZE, new Float(font.getFontHeightInPoints()));
		if (font.getBoldweight() == Font.BOLDWEIGHT_BOLD) {
			str.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, startIdx, endIdx);
		}
		if (font.getItalic()) {
			str.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, startIdx, endIdx);
		}
		if (font.getUnderline() == Font.U_SINGLE) {
			str.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, startIdx, endIdx);
		}
	}

	private static boolean containsCell(CellRangeAddress cellRange, int rowIndex, int colIndex) {
		return cellRange.getFirstRow() <= rowIndex && cellRange.getLastRow() >= rowIndex
			&& cellRange.getFirstColumn() <= colIndex && cellRange.getLastColumn() >= colIndex;
	}

	/**
	 * Adjusts the row height to fit the contents. This process can be
	 * relatively slow on large sheets, so this should normally only be called
	 * once per row, at the end of your processing. You can specify whether the
	 * content of merged cells should be considered or ignored. Default is to
	 * ignore merged cells.
	 * 
	 * @param useMergedCells
	 *            whether to use the contents of merged cells when calculating
	 *            the width of the column
	 */
	public static Double calculateRowSize(	Workbook workbook,
											Sheet sheet,
											Row row,
											boolean useMergedCells) {
		double height = -1;
		if (workbook != null && sheet != null && row != null) {
			for (Cell cell : row) {
				if (cell != null) {
					int cellIndex = cell.getColumnIndex();

					int widthInChars = sheet.getColumnWidth(cell.getColumnIndex());
					int mergedHeight = 1;
					if (useMergedCells) {
						for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
							CellRangeAddress region = sheet.getMergedRegion(i);
							if (containsCell(region, row.getRowNum(), cellIndex)) {
								cell =
										sheet.getRow(region.getFirstRow())
												.getCell(region.getFirstColumn());
								widthInChars = 0;
								for (int j = region.getFirstColumn(); j <= region.getLastColumn(); ++j) {
									widthInChars += sheet.getColumnWidth(j);
								}
								mergedHeight = region.getLastRow() - region.getFirstRow() + 1;
								break;
							}
						}
					}

					Font font = workbook.getFontAt(cell.getCellStyle().getFontIndex());
					double[] etalon_h = getCharDimensions(TEST_STRING_H, font);
					etalon_h[1] = 12.75;
					widthInChars /= 256;
					double newHeight = 0;
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						RichTextString rt = cell.getRichStringCellValue();
						String value = rt.getString();
						newHeight =
								getStringLinesCount(StringUtils.split(value, "\n\r"), //$NON-NLS-1$
													font,
													(float) (widthInChars * etalon_h[0]))
									* etalon_h[1];
					}
					height = Math.max(height, newHeight / mergedHeight);
				}
			}
		}
		return height >= 0 ? Math.min(height, Short.MAX_VALUE) : null;
	}

	public static Double calculateRowSize(	Workbook workbook,
											Sheet sheet,
											int rowIndex,
											boolean useMergedCells) {
		return calculateRowSize(workbook, sheet, sheet.getRow(rowIndex), useMergedCells);
	}

	public static void autoSizeRow(Workbook workbook, Sheet sheet, Row row, boolean useMergedCells) {
		Double height = calculateRowSize(workbook, sheet, row, useMergedCells);
		if (height != null) {
			row.setHeightInPoints(height.floatValue());
		}
	}

	public static void autoSizeRow(	Workbook workbook,
									Sheet sheet,
									int rowIndex,
									boolean useMergedCells) {
		autoSizeRow(workbook, sheet, sheet.getRow(rowIndex), useMergedCells);
	}

	private static final String TEST_STRING_H =
			"asdaklsjdklsajd|wwwwwwwwwwwwwwwwwwwww!@#$%^&*()lkjsalkdjlsakjdlsakqiowuiobv[]z1234567890_+-="; //$NON-NLS-1$

	@SuppressWarnings("unused")
	private static final String TEST_STRING_V =
			"LineBreakMeasurer is constructed with an iterator over styled text. The iterator's range should be a single paragraph in the text."; //$NON-NLS-1$

	private static double[] getCharDimensions(String string, Font font) {
		double[] sizes = getStringSizes(string, font);
		return new double[] { sizes[0] / string.length(), sizes[1] };
	}

	private static double[] getStringSizes(String string, Font font) {
		AttributedString str = new AttributedString(string);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		copyAttributes(font, str, 0, string.length() - 1);
		TextLayout layout = new TextLayout(str.getIterator(), frc);
		double width = layout.getBounds().getWidth();
		double height = layout.getBounds().getHeight();
		return new double[] { width, height };
	}

	private static int getStringLinesCount(String[] strings, Font font, float maxWidth) {
		int result = 0;
		for (String string : strings) {
			result += getStringLinesCount(string, font, maxWidth);
		}
		return result;
	}

	private static int getStringLinesCount(String string, Font font, float maxWidth) {
		if (StringUtils.length(string) <= 1) {
			return 1;
		}
		AttributedString str = new AttributedString(string);
		copyAttributes(font, str, 0, string.length() - 1);
		LineBreakMeasurer measurer =
				new LineBreakMeasurer(str.getIterator(), new FontRenderContext(null, true, true));
		int result = 0;
		while (measurer.nextLayout(maxWidth) != null) {
			result++;
		}
		return result;
	}
}
