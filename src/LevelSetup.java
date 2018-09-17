import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LevelSetup {

	public static BrickField[] makeLevels(String path) throws IOException {
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(Values.ERROR_FILE_NOT_FOUND + path);
		}
		
		ArrayList<String> file = new ArrayList<String>();
		
		String line;
		while((line = br.readLine()) != null) {
			file.add(line);
		}
		
		br.close();
		
		for(int i = 0; i < file.size(); i++) {
			line = file.get(i);
			if(line.length() == 0) {
				file.remove(i);
				i--;
			} else if(line.charAt(0) == '#') {
				file.remove(i);
				i--;
			} else if(line.length() != Values.BRICK_ARRAY_WIDTH)
				throw new RuntimeException(Values.ERROR_INVALID_FILE_FORMAT
						+ " -- bad line length ["+i+"]: "+line.length());
			
			// The line is now able to be handled by BrickField.makeField()
			// so it gets to stay
		}
		
		if(file.size() % Values.BRICK_ARRAY_HEIGHT != 0) {
			throw new RuntimeException(Values.ERROR_INVALID_FILE_FORMAT + " -- bad # of rows: " + file.size());
		}
		
		int numRows = Values.BRICK_ARRAY_HEIGHT;
		int numLevels = file.size() / numRows;
		String[][] levels = new String[numLevels][numRows];
		
		for(int lvl = 0; lvl < levels.length; lvl++) {
			for(int row = 0; row < levels[lvl].length; row++) {
				levels[lvl][row] = file.get(row + lvl * numRows);
			}
		}
		
		BrickField[] fields = new BrickField[levels.length];
		
		for(int i = 0; i < fields.length; i++) {
			fields[i] = BrickField.makeField(levels[i]);
		}
		
		return fields;
	}
	
	public static void addPlayerLevelToFile(BrickField newLevel) throws IOException {
		BrickField[] currentPlayerLevels;
		
		currentPlayerLevels = makeLevels(Values.PATH_PLAYER_LEVELS);
		
		BrickField[] playerLevels = new BrickField[currentPlayerLevels.length + 1];
		
		for(int i = 0; i < playerLevels.length; i++) {
			if(i == playerLevels.length - 1) {
				playerLevels[i] = newLevel;
			} else {
				playerLevels[i] = currentPlayerLevels[i];
			}
		}
		
		BufferedWriter bw;
		
		// make writer
		bw = new BufferedWriter(new FileWriter(Values.PATH_PLAYER_LEVELS));
		
		// write levels to file
		for(int i = 0; i < playerLevels.length; i++) {
			String[] rows = playerLevels[i].getLevelInFileFormat();
			for(String r : rows) {
				bw.write(r);
				bw.newLine();
			}
			bw.newLine();
		}
		
		// close writer
		bw.close();
	}
	
	public static BrickField[] getLevels() {
		try {
			return makeLevels(Values.PATH_LEVELS);
		} catch (IOException e) {
			throw new RuntimeException("ERROR READING " + Values.PATH_LEVELS);
		}
	}
	
	public static BrickField[] getPlayerLevels() {
		try {
			return makeLevels(Values.PATH_PLAYER_LEVELS);
		} catch (IOException e) {
			throw new RuntimeException("ERROR READING " + Values.PATH_PLAYER_LEVELS);
		}
	}
}
