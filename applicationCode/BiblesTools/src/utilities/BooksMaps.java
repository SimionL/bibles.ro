package utilities;

import java.util.HashMap;
import java.util.Map;

public class BooksMaps {

	private Map<String, Integer> CornilescuMapByIndex = getCornilescuBooksMapByIndex();
	private Map<String, String> CornilescuMapByString = getCornilescuBooksMapByString();

	public Map<String, Integer> getCornilescuMapByIndex() {
		return CornilescuMapByIndex;
	}

	public Map<String, String> getCornilescuMapByString() {
		return CornilescuMapByString;
	}

	private Map<String, Integer> getCornilescuBooksMapByIndex(){

		Map<String, Integer> CornilescuMap = new HashMap<>();

		CornilescuMap.put("Geneza", 1);
		CornilescuMap.put("Exodul", 2);
		CornilescuMap.put("Leviticul", 3);
		CornilescuMap.put("Numeri", 4);
		CornilescuMap.put("Deuteronomul", 5);
		CornilescuMap.put("Iosua", 6);
		CornilescuMap.put("Judecători", 7);
		CornilescuMap.put("Rut", 8);
		CornilescuMap.put("1 Samuel", 9);
		CornilescuMap.put("2 Samuel", 10);
		CornilescuMap.put("1 Împarati", 11);
		CornilescuMap.put("2 Împarati", 12);
		CornilescuMap.put("1 Cronici", 13);
		CornilescuMap.put("2 Cronici", 14);
		CornilescuMap.put("Ezra", 15);
		CornilescuMap.put("Neemia", 16);
		CornilescuMap.put("Estera", 17);
		CornilescuMap.put("Iov", 18);
		CornilescuMap.put("Psalmii", 19);
		CornilescuMap.put("Proverbe", 20);
		CornilescuMap.put("Eclesiastul", 21);
		CornilescuMap.put("Cântarea Cântarilor", 22);
		CornilescuMap.put("Isaia", 23);
		CornilescuMap.put("Ieremia", 24);
		CornilescuMap.put("Plângerile lui Ieremia", 25);
		CornilescuMap.put("Ezechiel", 26);
		CornilescuMap.put("Daniel", 27);
		CornilescuMap.put("Osea", 28);
		CornilescuMap.put("Ioel", 29);
		CornilescuMap.put("Amos", 30);
		CornilescuMap.put("Obadia", 31);
		CornilescuMap.put("Iona", 32);
		CornilescuMap.put("Mica", 33);
		CornilescuMap.put("Naum", 34);
		CornilescuMap.put("Habacuc", 35);
		CornilescuMap.put("Ţefania", 36);
		CornilescuMap.put("Hagai", 37);
		CornilescuMap.put("Zaharia", 38);
		CornilescuMap.put("Maleahi", 39);
		CornilescuMap.put("Matei", 40);
		CornilescuMap.put("Marcu", 41);
		CornilescuMap.put("Luca", 42);
		CornilescuMap.put("Ioan", 43);
		CornilescuMap.put("Faptele Apostolilor", 44);
		CornilescuMap.put("Romani", 45);
		CornilescuMap.put("1 Corinteni", 46);
		CornilescuMap.put("2 Corinteni", 47);
		CornilescuMap.put("Galateni", 48);
		CornilescuMap.put("Efeseni", 49);
		CornilescuMap.put("Filipeni", 50);
		CornilescuMap.put("Coloseni", 51);
		CornilescuMap.put("1 Tesaloniceni", 52);
		CornilescuMap.put("2 Tesaloniceni", 53);
		CornilescuMap.put("1 Timotei", 54);
		CornilescuMap.put("2 Timotei", 55);
		CornilescuMap.put("Tit", 56);
		CornilescuMap.put("Filimon", 57);
		CornilescuMap.put("Evrei", 58);
		CornilescuMap.put("Iacov", 59);
		CornilescuMap.put("1 Petru", 60);
		CornilescuMap.put("2 Petru", 61);
		CornilescuMap.put("1 Ioan", 62);
		CornilescuMap.put("2 Ioan", 63);
		CornilescuMap.put("3 Ioan", 64);
		CornilescuMap.put("Iuda", 65);
		CornilescuMap.put("Apocalipsa", 66);

		return CornilescuMap;
	}
	
	private Map<String, String> getCornilescuBooksMapByString(){

		Map<String, String> CornilescuMap = new HashMap<>();

		CornilescuMap.put("GENESA (FACEREA)", "Geneza");
		CornilescuMap.put("EXODUL (IEŞIREA)", "Exodul");
		CornilescuMap.put("LEVITICUL", "Leviticul");
		CornilescuMap.put("NUMERI", "Numeri");
		CornilescuMap.put("DEUTERONOMUL (A DOUA LEGE)", "Deuteronomul");
		CornilescuMap.put("IOSUA", "Iosua");
		CornilescuMap.put("JUDECĂTORII", "Judecători");
		CornilescuMap.put("RUT", "Rut");
		CornilescuMap.put("CARTEA ÎNTÂI A LUI SAMUEL", "1 Samuel");
		CornilescuMap.put("A DOUA CARTE A LUI SAMUEL", "2 Samuel");
		CornilescuMap.put("CARTEA ÎNTÂIA A ÎMPĂRAŢILOR", "1 Împarati");
		CornilescuMap.put("A DOUA CARTEA A ÎMPĂRAŢILOR", "2 Împarati");
		CornilescuMap.put("CARTEA ÎNTÂI A CRONICILOR", "1 Cronici");
		CornilescuMap.put("A DOUA CARTEA A CRONICILOR", "2 Cronici");
		CornilescuMap.put("EZRA", "Ezra");
		CornilescuMap.put("NEEMIA", "Neemia");
		CornilescuMap.put("ESTERA", "Estera");
		CornilescuMap.put("IOV", "Iov");
		CornilescuMap.put("PSALMII", "Psalmii");
		CornilescuMap.put("PILDELE", "Proverbe");
		CornilescuMap.put("ECLESIASTUL", "Eclesiastul");
		CornilescuMap.put("CÂNTAREA CÂNTĂRILOR", "Cântarea Cântarilor");
		CornilescuMap.put("ISAIA", "Isaia");
		CornilescuMap.put("IEREMIA", "Ieremia");
		CornilescuMap.put("PLÂNGERILE LUI IEREMIA", "Plângerile lui Ieremia");
		CornilescuMap.put("EZECHIEL", "Ezechiel");
		CornilescuMap.put("DANIEL", "Daniel");
		CornilescuMap.put("OSEA", "Osea");
		CornilescuMap.put("IOEL", "Ioel");
		CornilescuMap.put("AMOS", "Amos");
		CornilescuMap.put("OBADIA", "Obadia");
		CornilescuMap.put("IONA", "Iona");
		CornilescuMap.put("MICA", "Mica");
		CornilescuMap.put("NAUM", "Naum");
		CornilescuMap.put("HABACUC", "Habacuc");
		CornilescuMap.put("ŢEFANIA", "Ţefania");
		CornilescuMap.put("HAGAI", "Hagai");
		CornilescuMap.put("ZAHARIA", "Zaharia");
		CornilescuMap.put("MALEAHI", "Maleahi");
		CornilescuMap.put("EVANGHELIA DUPĂ MATEI", "Matei");
		CornilescuMap.put("EVANGHELIA DUPĂ MARCU", "Marcu");
		CornilescuMap.put("EVANGHELIA DUPĂ LUCA", "Luca");
		CornilescuMap.put("EVANGHELIA DUPĂ IOAN", "Ioan");
		CornilescuMap.put("FAPTELE APOSTOLILOR", "Faptele Apostolilor");
		CornilescuMap.put("EPISTOLA LUI PAVEL CĂTRE ROMANI", "Romani");
		CornilescuMap.put("ÎNTÂIA EPISTOLĂ A LUI PAVEL CĂTRE CORINTENI", "1 Corinteni");
		CornilescuMap.put("A DOUA EPISTOLĂ A LUI PAVEL CĂTRE CORINTENI", "2 Corinteni");
		CornilescuMap.put("EPISTOLA LUI PAVEL CĂTRE GALATENI", "Galateni");
		CornilescuMap.put("EPISTOLA LUI PAVEL CĂTRE EFESENI", "Efeseni");
		CornilescuMap.put("EPISTOLA LUI PAVEL CĂTRE FILIPENI", "Filipeni");
		CornilescuMap.put("EPISTOLA LUI PAVEL CĂTRE COLOSENI", "Coloseni");
		CornilescuMap.put("EPISTOLA ÎNTÂI A LUI PAVEL CĂTRE TESALONICENI", "1 Tesaloniceni");
		CornilescuMap.put("EPISTOLA A DOUA A LUI PAVEL CĂTRE TESALONICENI", "2 Tesaloniceni");
		CornilescuMap.put("EPISTOLA ÎNTÂI A LUI PAVEL CĂTRE TIMOTEI", "1 Timotei");
		CornilescuMap.put("EPISTOLA A DOUA A LUI PAVEL CĂTRE TIMOTEI", "2 Timotei");
		CornilescuMap.put("EPISTOLA LUI PAVEL CĂTRE TIT", "Tit");
		CornilescuMap.put("EPISTOLA LUI PAVEL CĂTRE FILIMON", "Filimon");
		CornilescuMap.put("EPISTOLA LUI PAVEL CĂTRE EVREI", "Evrei");
		CornilescuMap.put("EPISTOLA SOBORNICEASCĂ A LUI IACOV", "Iacov");
		CornilescuMap.put("ÎNTÂIA EPISTOLĂ SOBORNICEASCĂ A LUI PETRU", "1 Petru");
		CornilescuMap.put("A DOUA EPISTOLĂ SOBORNICEASCĂ A LUI PETRU", "2 Petru");
		CornilescuMap.put("ÎNTÂIA EPISTOLĂ SOBORNICEASCĂ A LUI IOAN", "1 Ioan");
		CornilescuMap.put("A DOUA EPISTOLĂ A LUI IOAN", "2 Ioan");
		CornilescuMap.put("A TREIA EPISTOLĂ A LUI IOAN", "3 Ioan");
		CornilescuMap.put("EPISTOLA SOBORNICEASCĂ A LUI IUDA", "Iuda");
		CornilescuMap.put("APOCALIPSA LUI IOAN", "Apocalipsa");

		return CornilescuMap;
	}
	public Map<Integer, String> getCornilescuBooksMapByNumber(){

		Map<Integer, String> CornilescuMap = new HashMap<>();

		CornilescuMap.put(1,"Geneza");
		CornilescuMap.put(2,"Exodul");
		CornilescuMap.put(3,"Leviticul");
		CornilescuMap.put(4,"Numeri");
		CornilescuMap.put(5,"Deuteronomul");
		CornilescuMap.put(6,"Iosua");
		CornilescuMap.put(7,"Judecători");
		CornilescuMap.put(8,"Rut");
		CornilescuMap.put(9,"1 Samuel");
		CornilescuMap.put(10,"2 Samuel");
		CornilescuMap.put(11,"1 Împarati");
		CornilescuMap.put(12,"2 Împarati");
		CornilescuMap.put(13,"1 Cronici");
		CornilescuMap.put(14,"2 Cronici");
		CornilescuMap.put(15,"Ezra");
		CornilescuMap.put(16,"Neemia");
		CornilescuMap.put(17,"Estera");
		CornilescuMap.put(18,"Iov");
		CornilescuMap.put(19,"Psalmii");
		CornilescuMap.put(20,"Proverbe");
		CornilescuMap.put(21,"Eclesiastul");
		CornilescuMap.put(22,"Cântarea Cântarilor");
		CornilescuMap.put(23,"Isaia");
		CornilescuMap.put(24,"Ieremia");
		CornilescuMap.put(25,"Plângerile lui Ieremia");
		CornilescuMap.put(26,"Ezechiel");
		CornilescuMap.put(27,"Daniel");
		CornilescuMap.put(28,"Osea");
		CornilescuMap.put(29,"Ioel");
		CornilescuMap.put(30,"Amos");
		CornilescuMap.put(31,"Obadia");
		CornilescuMap.put(32,"Iona");
		CornilescuMap.put(33,"Mica");
		CornilescuMap.put(34,"Naum");
		CornilescuMap.put(35,"Habacuc");
		CornilescuMap.put(36,"Ţefania");
		CornilescuMap.put(37,"Hagai");
		CornilescuMap.put(38,"Zaharia");
		CornilescuMap.put(39,"Maleahi");
		CornilescuMap.put(40,"Matei");
		CornilescuMap.put(41,"Marcu");
		CornilescuMap.put(42,"Luca");
		CornilescuMap.put(43,"Ioan");
		CornilescuMap.put(44,"Faptele Apostolilor");
		CornilescuMap.put(45,"Romani");
		CornilescuMap.put(46,"1 Corinteni");
		CornilescuMap.put(47,"2 Corinteni");
		CornilescuMap.put(48,"Galateni");
		CornilescuMap.put(49,"Efeseni");
		CornilescuMap.put(50,"Filipeni");
		CornilescuMap.put(51,"Coloseni");
		CornilescuMap.put(52,"1 Tesaloniceni");
		CornilescuMap.put(53,"2 Tesaloniceni");
		CornilescuMap.put(54,"1 Timotei");
		CornilescuMap.put(55,"2 Timotei");
		CornilescuMap.put(56,"Tit");
		CornilescuMap.put(57,"Filimon");
		CornilescuMap.put(58,"Evrei");
		CornilescuMap.put(59,"Iacov");
		CornilescuMap.put(60,"1 Petru");
		CornilescuMap.put(61,"2 Petru");
		CornilescuMap.put(62,"1 Ioan");
		CornilescuMap.put(63,"2 Ioan");
		CornilescuMap.put(64,"3 Ioan");
		CornilescuMap.put(65,"Iuda");
		CornilescuMap.put(66,"Apocalipsa");

		return CornilescuMap;
	}
	public Map<Integer, String> getGenericBooksMapByNumber(){

		Map<Integer, String> genericMap = new HashMap<>();

		genericMap.put(1,"genesis");
		genericMap.put(2,"exodus");
		genericMap.put(3,"leviticus");
		genericMap.put(4,"numbers");
		genericMap.put(5,"deuteronomy");
		genericMap.put(6,"joshua");
		genericMap.put(7,"judges");
		genericMap.put(8,"ruth");
		genericMap.put(9,"1_samuel");
		genericMap.put(10,"2_samuel");
		genericMap.put(11,"1_kings");
		genericMap.put(12,"2_kings");
		genericMap.put(13,"1_chronicles");
		genericMap.put(14,"2_chronicles");
		genericMap.put(15,"ezra");
		genericMap.put(16,"nehemiah");
		genericMap.put(17,"esther");
		genericMap.put(18,"job");
		genericMap.put(19,"psalms");
		genericMap.put(20,"proverbs");
		genericMap.put(21,"ecclesiastes");
		genericMap.put(22,"songs");
		genericMap.put(23,"isaiah");
		genericMap.put(24,"jeremiah");
		genericMap.put(25,"lamentations");
		genericMap.put(26,"ezekiel");
		genericMap.put(27,"daniel");
		genericMap.put(28,"hosea");
		genericMap.put(29,"joel");
		genericMap.put(30,"amos");
		genericMap.put(31,"obadiah");
		genericMap.put(32,"jonah");
		genericMap.put(33,"micah");
		genericMap.put(34,"nahum");
		genericMap.put(35,"habakkuk");
		genericMap.put(36,"zephaniah");
		genericMap.put(37,"haggai");
		genericMap.put(38,"zechariah");
		genericMap.put(39,"malachi");
		genericMap.put(40,"matthew");
		genericMap.put(41,"mark");
		genericMap.put(42,"luke");
		genericMap.put(43,"john");
		genericMap.put(44,"acts");
		genericMap.put(45,"romans");
		genericMap.put(46,"1_corinthians");
		genericMap.put(47,"2_corinthians");
		genericMap.put(48,"galatians");
		genericMap.put(49,"ephesians");
		genericMap.put(50,"philippians");
		genericMap.put(51,"colossians");
		genericMap.put(52,"1_thessalonians");
		genericMap.put(53,"2_thessalonians");
		genericMap.put(54,"1_timothy");
		genericMap.put(55,"2_timothy");
		genericMap.put(56,"titus");
		genericMap.put(57,"philemon");
		genericMap.put(58,"hebrews");
		genericMap.put(59,"james");
		genericMap.put(60,"1_peter");
		genericMap.put(61,"2_peter");
		genericMap.put(62,"1_john");
		genericMap.put(63,"2_john");
		genericMap.put(64,"3_john");
		genericMap.put(65,"jude");
		genericMap.put(66,"revelation");

		return genericMap;
	}
	public Map<String, Integer> getCornilescuBooksStatistics(){

		Map<String, Integer> CornilescuMap = new HashMap<>();

		CornilescuMap.put("Geneza", 50);
		CornilescuMap.put("Exodul", 40);
		CornilescuMap.put("Leviticul", 27);
		CornilescuMap.put("Numeri", 36);
		CornilescuMap.put("Deuteronomul", 34);
		CornilescuMap.put("Iosua", 24);
		CornilescuMap.put("Judecători", 21);
		CornilescuMap.put("Rut", 4);
		CornilescuMap.put("1 Samuel", 31);
		CornilescuMap.put("2 Samuel", 24);
		CornilescuMap.put("1 Împarati", 22);
		CornilescuMap.put("2 Împarati", 25);
		CornilescuMap.put("1 Cronici", 29);
		CornilescuMap.put("2 Cronici", 36);
		CornilescuMap.put("Ezra", 10);
		CornilescuMap.put("Neemia", 13);
		CornilescuMap.put("Estera", 10);
		CornilescuMap.put("Iov", 42);
		CornilescuMap.put("Psalmii", 150);
		CornilescuMap.put("Proverbe", 31);
		CornilescuMap.put("Eclesiastul", 12);
		CornilescuMap.put("Cântarea Cântarilor", 8);
		CornilescuMap.put("Isaia", 66);
		CornilescuMap.put("Ieremia", 52);
		CornilescuMap.put("Plângerile lui Ieremia", 5);
		CornilescuMap.put("Ezechiel", 48);
		CornilescuMap.put("Daniel", 12);
		CornilescuMap.put("Osea", 14);
		CornilescuMap.put("Ioel", 3);
		CornilescuMap.put("Amos", 9);
		CornilescuMap.put("Obadia", 1);
		CornilescuMap.put("Iona", 4);
		CornilescuMap.put("Mica", 7);
		CornilescuMap.put("Naum", 3);
		CornilescuMap.put("Habacuc", 3);
		CornilescuMap.put("Ţefania", 3);
		CornilescuMap.put("Hagai", 2);
		CornilescuMap.put("Zaharia", 14);
		CornilescuMap.put("Maleahi", 4);
		CornilescuMap.put("Matei", 28);
		CornilescuMap.put("Marcu", 16);
		CornilescuMap.put("Luca", 24);
		CornilescuMap.put("Ioan", 21);
		CornilescuMap.put("Faptele Apostolilor", 28);
		CornilescuMap.put("Romani", 16);
		CornilescuMap.put("1 Corinteni", 16);
		CornilescuMap.put("2 Corinteni", 13);
		CornilescuMap.put("Galateni", 6);
		CornilescuMap.put("Efeseni", 6);
		CornilescuMap.put("Filipeni", 4);
		CornilescuMap.put("Coloseni", 4);
		CornilescuMap.put("1 Tesaloniceni", 5);
		CornilescuMap.put("2 Tesaloniceni", 3);
		CornilescuMap.put("1 Timotei", 6);
		CornilescuMap.put("2 Timotei", 4);
		CornilescuMap.put("Tit", 3);
		CornilescuMap.put("Filimon", 1);
		CornilescuMap.put("Evrei", 13);
		CornilescuMap.put("Iacov", 5);
		CornilescuMap.put("1 Petru", 5);
		CornilescuMap.put("2 Petru", 3);
		CornilescuMap.put("1 Ioan", 5);
		CornilescuMap.put("2 Ioan", 1);
		CornilescuMap.put("3 Ioan", 1);
		CornilescuMap.put("Iuda", 1);
		CornilescuMap.put("Apocalipsa", 22);

		return CornilescuMap;
	}
}