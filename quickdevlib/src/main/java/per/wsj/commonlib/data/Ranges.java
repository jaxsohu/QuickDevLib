package per.wsj.commonlib.downloader.data;

/**
 * 记录断点信息
 */
public class Ranges {
    public long[] start;
    public long[] end;

    public Ranges(long[] start, long[] end) {
        this.start = start;
        this.end = end;
    }
}
