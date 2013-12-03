class ProfileRNC 

	attr_accessor :rnc, :nombre, :nombre_comercial,
					:categoria, :regimen_de_pago, :estatus

	def initialize(args)
		@rnc = args[0]
		@nombre = args[1]
		@nombre_comercial = args[2]
		@categoria = args[3]
		@regimen_de_pago = args[4]
		@estatus = args[5]
	end

end