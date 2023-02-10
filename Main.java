import java.io.*;
import java.util.Scanner;
public class htmlConverter {
	
	public static Scanner input = new Scanner(System.in); 
	public static void main(String[]args) throws FileNotFoundException{ 
		// the "FileNoFoundException" will prevent the error of that there is no initial file to start with
		System.out.println("This program converts txt files to html ones");
		System.out.println("Rules:");
		System.out.println("0. File names have to be typed in the format of insert File name.txt: ");
		System.out.println("1. Hyperlinks have to be in [[insert link]]");
		System.out.println("2. Main titles have to be _insert text_");
		System.out.println("3. Lesser titles have be _insert text");
		System.out.println("4: Bulletin points must have a \" - \" at the beginning");
		System.out.println("5. To create a line that spans horizontally, type ==");
		System.out.println("6: Images must start with img_insert web link of image");
		System.out.println("7: Videos must start with video_insert web link of video");
		System.out.println("8. To insert background images, they must start with background_insert link of the image");
		System.out.println("9. To put a line of text in a box, they must start with [[[insert text here");
		
		String file = input.nextLine();
		String withoutxt = file.substring(0, file.length() - 4);
		//"file.length(0, file.length() - 4); removes the ".txt" tag at the end of the name
        String withtml = withoutxt + ".html";
        String text = ".txt";
		
		//Declaring an alternative version of the original filename, we add ".html"
		//Converts the actual file to an html
		System.out.println("The new name of " + file +" will be: " + withtml );//withtml
		
		String underscore = "_";
		String list = "-";
		String listBackground = "-(back)";
		String imagestart = "img_";
		String videostart = "video_";
		String background = "background";
		String bold = "{";
		String block = "[[[";
		//our parameters for rule matching
		
		boolean first = true;
		boolean last = false;
		//controls the item block
		
		Scanner filereader = new Scanner(new File(file));
		PrintStream converter = new PrintStream(new File(withtml));
		//Printstream function is an output function that takes the new name of the file
		//Adds new addition to the lines within the file depending on the if statements within

		converter.print("<html>");
		converter.println("");
		converter.print("<body>");
		converter.println("");
		//beggining of the html file
		
		while(filereader.hasNextLine()) {
			String line = filereader.nextLine();
			
			if(last==true) {
				if(!line.startsWith(list)) {
					converter.println("</ul>");
					last = false;
					first = true;
				}
			}
			
			if(line.length() == 0) {
				converter.println("<p>");
				//adds a spaces in html
				//Spaces in html are denoted in a line as <p> for the entire line
			}
			else if(line.startsWith("=")&&line.endsWith("=")) {
				converter.println("<hr>");
			}else { 
				if(line.startsWith(underscore)&&line.endsWith(underscore)) {
				converter.println("<h1>"+line.substring(1, line.length()-1)+"</h1>");
				}
				else if(line.startsWith(underscore)) {
					converter.println("<h2>"+line.substring(1, line.length())+"</h2>");
				}
				else if(line.startsWith(bold)) {
					converter.println("<b>"+line.substring(1, line.length())+"</b>");
				}
				else if(line.startsWith(block)) {
					converter.println("<p style=\"border: 1px solid block\">" + line.substring(1, line.length()) + "</p>");
				}
				else if(line.startsWith(imagestart)){
					converter.println("<img src=\" "+line.substring(4, line.length())+"\"alt=\"\">");
				}
				else if(line.startsWith(videostart)){
					converter.println("<iframe width=\"1280\" height=\"\720\" src=\""+line.substring(6, line.length())+"\"></iframe>");
				}
                else if(line.startsWith(listBackground)) {
					
					String linkage = line.substring(0, line.length());
					String linkvage = urlembed(linkage);
					converter.println(linkvage);
					
					last = true;
					if(first == true) {
						converter.println("<p>");
						first = false;	
					}	
				}
				else if(line.startsWith(list)) {
					
					last = true;
					if(first == true) {
						converter.println("<ul>");
						first = false;	
					}
					
					String linkage = line.substring(1, line.length());
					String linkvage = urlembed(linkage);
					converter.println("<li>"+linkvage+"</li>");
					
				}else {
					String linkvage = urlembed(line);
					converter.println(linkvage + "</br>");
				}
			}
			
			
		}
		converter.println("</body>");
		converter.println("</html>");
	}
	
		public static String urlembed(String linkage) {
			String urlReturn = "";
			String firstUrl = "[[";
			String lastUrl = "]]";
			
			Scanner urlfinder = new Scanner(linkage);
			//This is scanner is exclusively for the url check
			
			while(urlfinder.hasNext()) {
				String lineInc = urlfinder.next();
				if(lineInc.startsWith(firstUrl)&&lineInc.endsWith(lastUrl)) {
					String lineInc2 = lineInc.substring(2, lineInc.length() -2);
					
					String newUrl = "<a href =\"" + lineInc2 + "\">" + lineInc2 + "</a>";
					urlReturn += newUrl + "\s";
				}else {
					urlReturn += lineInc + "\s";
				}
			}
			return urlReturn.substring(0, urlReturn.length()-1);
		}
		public static String begginingHTML(String initiate) {
			String newbody = "";
			String background = "(back)";
			Scanner declare = new Scanner(initiate);
			while(declare.hasNext()) {
				String after = declare.next();
				while(after.startsWith(background)){
					newbody = "background-image: url(\'" + after.substring(6, after.length()) + "\');";
				}
			}
			return newbody;
		}
		public void backgroundimagefirst() {
			 System.out.println("<html>");
			 System.out.println("<head>");
			 System.out.println("<style>");
			 System.out.println("body {");
		}
		public void backgroundimagesecond() {
			 System.out.println("}");
			 System.out.println("</style>");
			 System.out.println("</head>");
			 System.out.println("<body>");
			 System.out.println("</head>");
		}

        public void regularHtmlStart() {
        	System.out.print("<html>");
	        System.out.print("<body>");
        }
        public void regularHtmlEnd() {
        	System.out.print("</body");
        	System.out.print("</html>");
        }
}
