package hwplib.tool.paragraphadder;

import hwplib.object.bodytext.paragraph.charshape.CharPositonShapeIdPair;
import hwplib.object.bodytext.paragraph.charshape.ParaCharShape;
import hwplib.tool.paragraphadder.docinfo.DocInfoAdder;

public class ParaCharShapeCopyer {
	public static void copy(ParaCharShape source, ParaCharShape target, DocInfoAdder docInfoAdder) {
		for (CharPositonShapeIdPair cpsp : source.getPositonShapeIdPairList()) {
			target.addParaCharShape(cpsp.getPositon(), docInfoAdder.forCharShape().processById((int) cpsp.getShapeId()));
		}
	}
}
