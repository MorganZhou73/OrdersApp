import { envConfig } from '../../environment-config';

export class AppConstants {
	public static readonly URLS = envConfig();
	public static readonly OrderTypes = [ 'internet', 'tv', 'bundle' ];
}
