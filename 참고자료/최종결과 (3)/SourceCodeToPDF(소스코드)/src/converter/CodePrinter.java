package converter;
import java.io.*;
import java.util.Vector;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import option.*;

/**
 * 토큰화된 소스코드 파일을 PDF로 출력합니다.
 * @author 강승민
 *
 */
public class CodePrinter {

	/**
	 * 옵션 정보
	 */
	private Option option;
	
	/**
	 * 보안 정보
	 */
	private Security security;
	
	/**
	 * 생성자
	 * @param op 옵션 정보
	 * @param sec 보안 정보
	 */
	public CodePrinter(Option op, Security sec){
		option = op;
		security = sec;
	}
	
	/**
	 * 파싱된 모든 토큰을 한개의 PDF 문서에 출력합니다.
	 * @param tokens 토큰 벡터 / [패키지 리스트[패키지 내 파일 리스트 [파일 내 토큰 벡터]]]의 구성입니다.
	 * @param subName PDF 문서에 삽입되는 파일 이름 리스트 입니다.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void printAll(Vector<Vector<Vector<Token>>> tokens, Vector<String> subName) throws DocumentException, IOException{
		Document doc = new Document(option.getPageType(), option.getMarginLeft(), option.getMarginRight(), option.getMarginTop(), option.getMarginBottom());
		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(option.getSaveLocate()+option.getFileName() + ".pdf"));
		} catch (FileNotFoundException e1) {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(option.getSaveLocate()+ option.getFileName() + (int)(Math.random() * 10000) + ".pdf"));
		} catch (DocumentException e2) {
			e2.printStackTrace();
		}
		if(security.getUseSecurityOption()){
			if(security.getUseCopyMode()){
				writer.setEncryption(security.getPassword().getBytes(), setOwnerPassword().getBytes(), PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_40);
			}else{
				writer.setEncryption(security.getPassword().getBytes(), setOwnerPassword().getBytes(), PdfWriter.ALLOW_SCREENREADERS, PdfWriter.STANDARD_ENCRYPTION_40);
			}
		}
		CopyRightEvent event = new CopyRightEvent(option);
		writer.setPageEvent(event);
		
		doc.addTitle(option.getFileName());
		
		doc.open();
		
		for(int i = 0 ; i < subName.size() ; i++){
			Anchor anc = new Anchor(subName.get(i));
			anc.setReference("#" + subName.get(i));
			Paragraph p = new Paragraph();
			p.add(anc);
			doc.add(p);
		}
		doc.newPage();
		
		int cnt = 0;
		
		for(int k = 0 ; k < tokens.size() ; k++){
			Vector<Vector<Token>> packageToken = tokens.get(k);
			for(int j = 0 ; j < packageToken.size() ; j++, cnt++){
				int codeLine = 1;
				Vector<Token> docToken = packageToken.get(j);
				
				Anchor anc = new Anchor(subName.get(cnt) + "\n");
				anc.setName(subName.get(cnt));
				Paragraph para = new Paragraph();
				para.add(anc);
				doc.add(para);
				
				Phrase p = new Phrase();
				if(option.getShowLineNumber()){
					p.add(new Chunk(codeLine + "    ", option.getLineNumberFont().getFont()));
					codeLine++;
				}
				doc.add(p);
				for(int i = 0 ; i < docToken.size() ; i++){
					Token t = docToken.get(i);
					p = new Phrase();
					String codeLineString = String.valueOf(codeLine) + "    ";
					if(t.getType() == Token.tokenType.NORMAL){
						if(t.getData().contains("\n")){
							p.add(new Chunk(t.getData(), option.getBasicFont().getFont()));
							if(option.getShowLineNumber()){
								p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
								codeLine++;
							}
						}else{
							p.add(new Chunk(t.getData(), option.getBasicFont().getFont()));
						}
					}else if(t.getType() == Token.tokenType.KEYWORD){
						if(t.getData().contains("\n")){
							p.add(new Chunk(t.getData(), option.getKeywordFont().getFont()));
							if(option.getShowLineNumber()){
								p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
								codeLine++;
							}
						}else{
							p.add(new Chunk(t.getData(), option.getKeywordFont().getFont()));
						}
					}else if(t.getType() == Token.tokenType.STRING || t.getType() == Token.tokenType.MULTIPLE_LINE_STRING){
						if(t.getData().contains("\n")){
							p.add(new Chunk(t.getData(), option.getStringFont().getFont()));
							if(option.getShowLineNumber()){
								p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
								codeLine++;
							}
						}else{
							p.add(new Chunk(t.getData(), option.getStringFont().getFont()));
						}
					}else if(t.getType() == Token.tokenType.COMMENT || t.getType() == Token.tokenType.MULTIPLE_LINE_COMMENT){
						if(t.getData().contains("\n")){
							p.add(new Chunk(t.getData(), option.getCommentFont().getFont()));
							if(option.getShowLineNumber()){
								p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
								codeLine++;
							}
						}else{
							p.add(new Chunk(t.getData(), option.getCommentFont().getFont()));
						}
					}
					doc.add(p);
				}
				doc.newPage();
			}
		}
		doc.close();
	}
	
	/**
	 * 파싱된 모든 토큰을 각 패키지(폴더) 별로 PDF 문서로 만듭니다.
	 * @param tokens 토큰 벡터 / [패키지 내 파일 리스트 [파일 내 토큰 벡터]] 구성입니다.
	 * @param name 패키지 이름입니다.
	 * @param subName 패키지 내에 포함된 파일 이름입니다.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void printPackage(Vector<Vector<Token>> tokens, String name, Vector<String> subName) throws DocumentException, IOException{
		Document doc = new Document(option.getPageType(), option.getMarginLeft(), option.getMarginRight(), option.getMarginTop(), option.getMarginBottom());
		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(option.getSaveLocate()+ name + ".pdf"));
		} catch (FileNotFoundException e1) {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(option.getSaveLocate()+ name + (int)(Math.random() * 10000) + ".pdf"));
		} catch (DocumentException e2) {
			e2.printStackTrace();
		}
		if(security.getUseSecurityOption()){
			if(security.getUseCopyMode()){
				writer.setEncryption(security.getPassword().getBytes(), setOwnerPassword().getBytes(), PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_40);
			}else{
				writer.setEncryption(security.getPassword().getBytes(), setOwnerPassword().getBytes(), PdfWriter.ALLOW_SCREENREADERS, PdfWriter.STANDARD_ENCRYPTION_40);
			}
		}
		CopyRightEvent event = new CopyRightEvent(option);
		writer.setPageEvent(event);
		
		doc.addTitle(name);
		
		doc.open();
		
		for(int i = 0 ; i < subName.size() ; i++){
			Anchor anc = new Anchor(subName.get(i));
			anc.setReference("#" + subName.get(i));
			Paragraph p = new Paragraph();
			p.add(anc);
			doc.add(p);
		}
		doc.newPage();
		
		for(int k = 0 ; k < tokens.size() ; k++){
			int codeLine = 1;
			Vector<Token> docToken = tokens.get(k);
			Anchor anc = new Anchor(subName.get(k) + "\n");
			anc.setName(subName.get(k));
			Paragraph para = new Paragraph();
			para.add(anc);
			doc.add(para);
			
			Phrase p = new Phrase();
			if(option.getShowLineNumber()){
				p.add(new Chunk(codeLine + "    ", option.getLineNumberFont().getFont()));
				codeLine++;
			}
			doc.add(p);
			for(int i = 0 ; i < docToken.size() ; i++){
				Token t = docToken.get(i);
				p = new Phrase();
				String codeLineString = String.valueOf(codeLine) + "    ";
				if(t.getType() == Token.tokenType.NORMAL){
					if(t.getData().contains("\n")){
						p.add(new Chunk(t.getData(), option.getBasicFont().getFont()));
						if(option.getShowLineNumber()){
							p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
							codeLine++;
						}
					}else{
						p.add(new Chunk(t.getData(), option.getBasicFont().getFont()));
					}
				}else if(t.getType() == Token.tokenType.KEYWORD){
					if(t.getData().contains("\n")){
						p.add(new Chunk(t.getData(), option.getKeywordFont().getFont()));
						if(option.getShowLineNumber()){
							p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
							codeLine++;
						}
					}else{
						p.add(new Chunk(t.getData(), option.getKeywordFont().getFont()));
					}
				}else if(t.getType() == Token.tokenType.STRING || t.getType() == Token.tokenType.MULTIPLE_LINE_STRING){
					if(t.getData().contains("\n")){
						p.add(new Chunk(t.getData(), option.getStringFont().getFont()));
						if(option.getShowLineNumber()){
							p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
							codeLine++;
						}
					}else{
						p.add(new Chunk(t.getData(), option.getStringFont().getFont()));
					}
				}else if(t.getType() == Token.tokenType.COMMENT || t.getType() == Token.tokenType.MULTIPLE_LINE_COMMENT){
					if(t.getData().contains("\n")){
						p.add(new Chunk(t.getData(), option.getCommentFont().getFont()));
						if(option.getShowLineNumber()){
							p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
							codeLine++;
						}
					}else{
						p.add(new Chunk(t.getData(), option.getCommentFont().getFont()));
					}
				}
				doc.add(p);
			}
			doc.newPage();
		}
		doc.close();
	}
	
	/**
	 * 단일 파일에 대한 토큰을 PDF로 출력합니다.
	 * @param tokens 출력할 토큰 리스트입니다.
	 * @param name 출력할 파일 명 입니다.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void printSingleFile(Vector<Token> tokens, String name) throws DocumentException, IOException{
		int codeLine = 1;
		Document doc = new Document(option.getPageType(), option.getMarginLeft(), option.getMarginRight(), option.getMarginTop(), option.getMarginBottom());
		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(option.getSaveLocate()+name + ".pdf"));
		} catch (FileNotFoundException e1) {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(option.getSaveLocate()+ name + (int)(Math.random() * 10000) + ".pdf"));
		} catch (DocumentException e2) {
			e2.printStackTrace();
		}
		
		if(security.getUseSecurityOption()){
			if(security.getUseCopyMode()){
				writer.setEncryption(security.getPassword().getBytes(), setOwnerPassword().getBytes(), PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_40);
			}else{
				writer.setEncryption(security.getPassword().getBytes(), setOwnerPassword().getBytes(), PdfWriter.ALLOW_SCREENREADERS, PdfWriter.STANDARD_ENCRYPTION_40);
			}
		}
		CopyRightEvent event = new CopyRightEvent(option);
		writer.setPageEvent(event);
		
		doc.addTitle(name);
		
		doc.open();
		Phrase p = new Phrase();
		if(option.getShowLineNumber()){
			p.add(new Chunk(codeLine + "    ", option.getLineNumberFont().getFont()));
			codeLine++;
		}
		doc.add(p);
		for(int i = 0 ; i < tokens.size() ; i++){
			Token t = tokens.get(i);
			p = new Phrase();
			String codeLineString = String.valueOf(codeLine) + "    ";
			if(t.getType() == Token.tokenType.NORMAL){
				if(t.getData().contains("\n")){
					p.add(new Chunk(t.getData(), option.getBasicFont().getFont()));
					if(option.getShowLineNumber()){
						p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
						codeLine++;
					}
				}else{
					p.add(new Chunk(t.getData(), option.getBasicFont().getFont()));
				}
			}else if(t.getType() == Token.tokenType.KEYWORD){
				if(t.getData().contains("\n")){
					p.add(new Chunk(t.getData(), option.getKeywordFont().getFont()));
					if(option.getShowLineNumber()){
						p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
						codeLine++;
					}
				}else{
					p.add(new Chunk(t.getData(), option.getKeywordFont().getFont()));
				}
			}else if(t.getType() == Token.tokenType.STRING || t.getType() == Token.tokenType.MULTIPLE_LINE_STRING){
				if(t.getData().contains("\n")){
					p.add(new Chunk(t.getData(), option.getStringFont().getFont()));
					if(option.getShowLineNumber()){
						p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
						codeLine++;
					}
				}else{
					p.add(new Chunk(t.getData(), option.getStringFont().getFont()));
				}
			}else if(t.getType() == Token.tokenType.COMMENT || t.getType() == Token.tokenType.MULTIPLE_LINE_COMMENT){
				if(t.getData().contains("\n")){
					p.add(new Chunk(t.getData(), option.getCommentFont().getFont()));
					if(option.getShowLineNumber()){
						p.add(new Chunk(codeLineString, option.getLineNumberFont().getFont()));
						codeLine++;
					}
				}else{
					p.add(new Chunk(t.getData(), option.getCommentFont().getFont()));
				}
			}
			doc.add(p);
		}
		doc.close();
	}

	/**
	 * 임의의 ownerPassword를 생성합니다.
	 * @return 생성된 ownerPassword 입니다.
	 */
	private String setOwnerPassword(){
		StringBuffer randStr = new StringBuffer();
		for(int i = 0 ; i < 30 ; i++){
			int lower = (int) Math.round(Math.random());
			int r = (int) Math.floor(Math.random() * 26);
			if(lower == 0){
				randStr.append((char) (r + 'A'));
			}else{
				randStr.append((char) (r + 'a'));
			}
		}
		return security.getPassword() + randStr.toString();
	}
}
