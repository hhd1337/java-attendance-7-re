package attendance.converter;

import attendance.domain.Menu;

public class StringToMenuConverter implements Converter<String, Menu> {
    @Override
    public Menu convert(String source) {
        Menu menu = Menu.findBySymbol(source.trim());
        return menu;
    }
}
