package utils.resources;

public interface OriginPaths {
	
	String rootProjectDir();
	String projectClassesDir();
	
	public enum OriginsDevelop implements OriginPaths {
		FROM_FILESYSTEM_ROOT(DevelopmentConstants.fromRootDirToBin,DevelopmentConstants.fromRootDirToProjectDir),
		FROM_MAIN_CLASS(DevelopmentConstants.fromMainToBin,DevelopmentConstants.fromMainToProjectDir);
		
		private String classesPath;
		private String projectPath;
		
		private OriginsDevelop(String classes,String project) {
			this.classesPath = classes;
			this.projectPath = project;
		}
		
		@Override
		public String rootProjectDir() {
			return projectPath;
		}
		
		@Override
		public String projectClassesDir() {
			return classesPath;
		}
	}
	
	public enum OriginsProduction implements OriginPaths {
		FROM_FILESYSTEM_ROOT(ProductionConstants.fromRootDirToBin,ProductionConstants.fromRootDirToProjectDir),
		FROM_MAIN_CLASS(ProductionConstants.fromMainToBin,ProductionConstants.fromMainToProjectDir);
		
		private String classesPath;
		private String projectPath;
		
		private OriginsProduction(String classes,String project) {
			this.classesPath = classes;
			this.projectPath = project;
		}
		
		@Override
		public String rootProjectDir() {
			return projectPath;
		}
		
		@Override
		public String projectClassesDir() {
			return classesPath;
		}
	}
	
	static class Paths {
		public OriginPaths fromMainClass;
		public OriginPaths fromRoot;
	}
}
