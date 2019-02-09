//테이블을 생성 - 표지마다 이름을 생성!!

import java.io.UnsupportedEncodingException;

import hwplib.object.HWPFile;
import hwplib.object.bodytext.Section;
import hwplib.object.bodytext.control.ControlTable;
import hwplib.object.bodytext.control.ControlType;
import hwplib.object.bodytext.control.ctrlheader.CtrlHeaderGso;
import hwplib.object.bodytext.control.ctrlheader.gso.HeightCriterion;
import hwplib.object.bodytext.control.ctrlheader.gso.HorzRelTo;
import hwplib.object.bodytext.control.ctrlheader.gso.ObjectNumberSort;
import hwplib.object.bodytext.control.ctrlheader.gso.RelativeArrange;
import hwplib.object.bodytext.control.ctrlheader.gso.TextFlowMethod;
import hwplib.object.bodytext.control.ctrlheader.gso.TextHorzArrange;
import hwplib.object.bodytext.control.ctrlheader.gso.VertRelTo;
import hwplib.object.bodytext.control.ctrlheader.gso.WidthCriterion;
import hwplib.object.bodytext.control.ctrlheader.sectiondefine.TextDirection;
import hwplib.object.bodytext.control.gso.textbox.LineChange;
import hwplib.object.bodytext.control.gso.textbox.TextVerticalAlignment;
import hwplib.object.bodytext.control.table.Cell;
import hwplib.object.bodytext.control.table.DivideAtPageBoundary;
import hwplib.object.bodytext.control.table.ListHeaderForCell;
import hwplib.object.bodytext.control.table.Row;
import hwplib.object.bodytext.control.table.Table;
import hwplib.object.bodytext.paragraph.Paragraph;
import hwplib.object.bodytext.paragraph.charshape.ParaCharShape;
import hwplib.object.bodytext.paragraph.header.ParaHeader;
import hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import hwplib.object.bodytext.paragraph.lineseg.ParaLineSeg;
import hwplib.object.bodytext.paragraph.text.ParaText;
import hwplib.object.docinfo.BorderFill;
import hwplib.object.docinfo.borderfill.BackSlashDiagonalShape;
import hwplib.object.docinfo.borderfill.BorderThickness;
import hwplib.object.docinfo.borderfill.BorderType;
import hwplib.object.docinfo.borderfill.SlashDiagonalShape;
import hwplib.object.docinfo.borderfill.fillinfo.PatternFill;
import hwplib.object.docinfo.borderfill.fillinfo.PatternType;


public class makeTable {
	
	
	private HWPFile hwpFile;
	private ControlTable table;
	private Row row;
	private Cell cell;
	private int borderFillIDForCell;
	
	
    private int zOrder = 0;	

    public void makeTable(HWPFile hwpFile2, String student_name) {
		hwpFile = hwpFile2;
		createTableControlAtFirstParagraph();
		setCtrlHeaderRecord(); 
		setTableRecordFor2By2Cells();
		add2By2Cell(student_name);
	}

	private void createTableControlAtFirstParagraph() {
		Section firstSection = hwpFile.getBodyText().getSectionList().get(0);
		Paragraph firstParagraph = firstSection.getParagraph(0);
		
		// 문단에서 표 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
		firstParagraph.getText().addExtendCharForTable();
		
		// 문단에 표 컨트롤 추가한다.
		table = (ControlTable) firstParagraph.addNewControl(ControlType.Table); 
	}

	private void setCtrlHeaderRecord() {
		CtrlHeaderGso ctrlHeader = table.getHeader();
		ctrlHeader.getProperty().setLikeWord(false);
		ctrlHeader.getProperty().setApplyLineSpace(false);
		ctrlHeader.getProperty().setVertRelTo(VertRelTo.Para);
		ctrlHeader.getProperty().setVertRelativeArrange(RelativeArrange.TopOrLeft);
		ctrlHeader.getProperty().setHorzRelTo(HorzRelTo.Para);
		ctrlHeader.getProperty().setHorzRelativeArrange(RelativeArrange.TopOrLeft);
		ctrlHeader.getProperty().setVertRelToParaLimit(false);		
		ctrlHeader.getProperty().setAllowOverlap(false);
		ctrlHeader.getProperty().setWidthCriterion(WidthCriterion.Absolute);
        ctrlHeader.getProperty().setHeightCriterion(HeightCriterion.Absolute);
        ctrlHeader.getProperty().setProtectSize(false);
        ctrlHeader.getProperty().setTextFlowMethod(TextFlowMethod.Tight);
        ctrlHeader.getProperty().setTextHorzArrange(TextHorzArrange.BothSides);
        ctrlHeader.getProperty().setObjectNumberSort(ObjectNumberSort.Table);
        ctrlHeader.setxOffset(mmToHwp(150.0));
        ctrlHeader.setyOffset(mmToHwp(250.0));
        ctrlHeader.setWidth(mmToHwp(30.0)); 
        ctrlHeader.setHeight(mmToHwp(20.0));
        ctrlHeader.setzOrder(zOrder++);
        ctrlHeader.setOutterMarginLeft(0);
        ctrlHeader.setOutterMarginRight(0);
        ctrlHeader.setOutterMarginTop(0);
        ctrlHeader.setOutterMarginBottom(0);
 	}
	
	private long mmToHwp(double mm) {
		return (long)(mm * 72000.0f / 254.0f + 0.5f);
	}

	private void setTableRecordFor2By2Cells() {
		Table tableRecord = table.getTable();
		tableRecord.getProperty().setDivideAtPageBoundary(DivideAtPageBoundary.DivideByCell);
		tableRecord.getProperty().setAutoRepeatTitleRow(false);
		tableRecord.setRowCount(2);
		tableRecord.setColumnCount(2);
		tableRecord.setCellSpacing(0);
		tableRecord.setLeftInnerMargin(0); 
		tableRecord.setRightInnerMargin(0);
		tableRecord.setTopInnerMargin(0);
		tableRecord.setBottomInnerMargin(0);
		tableRecord.setBorderFillId(getBorderFillIDForTableOutterLine());
    	tableRecord.getCellCountOfRowList().add(2); 
    	tableRecord.getCellCountOfRowList().add(2);
	}

	private int getBorderFillIDForTableOutterLine() {
		BorderFill bf =  hwpFile.getDocInfo().addNewBorderFill();
        bf.getProperty().set3DEffect(false);
        bf.getProperty().setShadowEffect(false);
        bf.getProperty().setSlashDiagonalShape(SlashDiagonalShape.None);
        bf.getProperty().setBackSlashDiagonalShape(BackSlashDiagonalShape.None);
        bf.getLeftBorder().setType(BorderType.None);
        bf.getLeftBorder().setThickness(BorderThickness.MM0_5);
        bf.getLeftBorder().getColor().setValue(0x0);
        bf.getRightBorder().setType(BorderType.None);
        bf.getRightBorder().setThickness(BorderThickness.MM0_5);
        bf.getRightBorder().getColor().setValue(0x0);
        bf.getTopBorder().setType(BorderType.None);
        bf.getTopBorder().setThickness(BorderThickness.MM0_5);
        bf.getTopBorder().getColor().setValue(0x0);
        bf.getBottomBorder().setType(BorderType.None);
        bf.getBottomBorder().setThickness(BorderThickness.MM0_5);
        bf.getBottomBorder().getColor().setValue(0x0);        
        bf.setDiagonalSort(BorderType.None);
        bf.setDiagonalThickness(BorderThickness.MM0_5);
        bf.getDiagonalColor().setValue(0x0);
        
        bf.getFillInfo().getType().setPatternFill(true);
        bf.getFillInfo().createPatternFill();
        PatternFill pf = bf.getFillInfo().getPatternFill();
        pf.setPatternType(PatternType.None);
        pf.getBackColor().setValue(-1);
        pf.getPatternColor().setValue(0);
        
        return hwpFile.getDocInfo().getBorderFillList().size();
	}

	private void add2By2Cell(String student_name) {
		borderFillIDForCell = getBorderFillIDForCell();

		addFirstRow(student_name);	
	}

	private int getBorderFillIDForCell() {
		BorderFill bf =  hwpFile.getDocInfo().addNewBorderFill();
        bf.getProperty().set3DEffect(false);
        bf.getProperty().setShadowEffect(false);
        bf.getProperty().setSlashDiagonalShape(SlashDiagonalShape.None);
        bf.getProperty().setBackSlashDiagonalShape(BackSlashDiagonalShape.None);
        bf.getLeftBorder().setType(BorderType.Solid);
        bf.getLeftBorder().setThickness(BorderThickness.MM0_5);
        bf.getLeftBorder().getColor().setValue(0x0);
        bf.getRightBorder().setType(BorderType.Solid);
        bf.getRightBorder().setThickness(BorderThickness.MM0_5);
        bf.getRightBorder().getColor().setValue(0x0);
        bf.getTopBorder().setType(BorderType.Solid);
        bf.getTopBorder().setThickness(BorderThickness.MM0_5);
        bf.getTopBorder().getColor().setValue(0x0);
        bf.getBottomBorder().setType(BorderType.Solid);
        bf.getBottomBorder().setThickness(BorderThickness.MM0_5);
        bf.getBottomBorder().getColor().setValue(0x0);        
        bf.setDiagonalSort(BorderType.None);
        bf.setDiagonalThickness(BorderThickness.MM0_5);
        bf.getDiagonalColor().setValue(0x0);
        
        bf.getFillInfo().getType().setPatternFill(true);
        bf.getFillInfo().createPatternFill();
        PatternFill pf = bf.getFillInfo().getPatternFill();
        pf.setPatternType(PatternType.None);
        pf.getBackColor().setValue(-1);
        pf.getPatternColor().setValue(0);
        
        return hwpFile.getDocInfo().getBorderFillList().size();
	}

	private void addFirstRow(String student_name) {
		row = table.addNewRow();
		addLeftTopCell();
		addRightTopCell(student_name);
	}

	private void addLeftTopCell() {
		cell = row.addNewCell();
		setListHeaderForCell(0, 0);
		
		//왼쪽 셀 내용
		setParagraphForCell("이름");
	}

	private void setListHeaderForCell(int colIndex, int rowIndex) {
		ListHeaderForCell lh = cell.getListHeader();
        lh.setParaCount(1);
        lh.getProperty().setTextDirection(TextDirection.Horizontal);
        lh.getProperty().setLineChange(LineChange.Normal);
        lh.getProperty().setTextVerticalAlignment(TextVerticalAlignment.Center);
        lh.getProperty().setProtectCell(false);
        lh.getProperty().setEditableAtFormMode(false);
        lh.setColIndex(colIndex);
        lh.setRowIndex(rowIndex);
        lh.setColSpan(1);
        lh.setRowSpan(1);
        
        //테이블 가로 세로 길이 지정
        lh.setWidth(mmToHwp(30.0));
        lh.setHeight(mmToHwp(20.0));
        
        
        lh.setLeftMargin(0);
        lh.setRightMargin(0);
        lh.setTopMargin(0);
        lh.setBottomMargin(0);        
        lh.setBorderFillId(borderFillIDForCell);
        lh.setTextWidth(mmToHwp(40.0));
        lh.setFieldName("");
	}

	private void setParagraphForCell(String text) {
		Paragraph p = cell.getParagraphList().addNewParagraph();
		setParaHeader(p);
		setParaText(p, text);
		setParaCharShape(p);
		setParaLineSeg(p);
	}

	private void setParaHeader(Paragraph p) {
		ParaHeader ph = p.getHeader();
		ph.setLastInList(true);
		// 셀의 문단 모양을 이미 만들어진 문단 모양으로 사용함
		ph.setParaShapeId(1);	
		// 셀의 스타일을이미 만들어진 스타일으로 사용함
		ph.setStyleId((short) 1);
		ph.getDivideSort().setDivideSection(false);
		ph.getDivideSort().setDivideMultiColumn(false);
		ph.getDivideSort().setDividePage(false);
		ph.getDivideSort().setDivideColumn(false);
		ph.setCharShapeCount(1);
		ph.setRangeTagCount(0);
		ph.setLineAlignCount(1);
		ph.setInstanceID(0);
		ph.setIsMergedByTrack(0);
	}

	private void setParaText(Paragraph p, String text2) {
		p.createText();
		ParaText pt = p.getText();
		try {
			pt.addString(text2);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setParaCharShape(Paragraph p) {
		p.createCharShape();
	
		ParaCharShape pcs = p.getCharShape();
		// 셀의 글자 모양을 이미 만들어진 글자 모양으로 사용함
		pcs.addParaCharShape(0, 1);
	}


	private void setParaLineSeg(Paragraph p) {
		p.createLineSeg();
		
		ParaLineSeg pls = p.getLineSeg();
		LineSegItem lsi = pls.addNewLineSegItem();
		
		lsi.setLineVerticalPosition(0);
		lsi.setLineHeight(ptToLineHeight(10.0));
		lsi.setTextPartHeight(ptToLineHeight(10.0));
		lsi.setDistanceBaseLineToLineVerticalPosition(ptToLineHeight(10.0 * 0.85));
		lsi.setLineSpace(ptToLineHeight(3.0));
		lsi.setStartPositionFromColumn(0);
		lsi.setSegmentWidth((int) mmToHwp(40.0));
		lsi.getTag().setFirstSegmentAtLine(true);
		lsi.getTag().setLastSegmentAtLine(true);
	}
	
	private int ptToLineHeight(double pt) {
		
		return (int) (pt * 100.0f);
	}

	//오른쪽 셀 추가
	private void addRightTopCell(String student_name) {
		cell = row.addNewCell();
		setListHeaderForCell(1, 0);
		setParagraphForCell(student_name);
	}


	
	
}
